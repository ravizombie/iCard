#include <EEPROM.h>
#include "EEPROMAnything.h"

// This script works better if the shield and the Arduino are in sync.  A workaround
// to ensure this is to upload the script twice - let it finish uploading, 
// then upload again - to the Arduino.

const int buflen = 32;
const int mbuf = 16;
int ee_addr=0;

char imeichar[17];
String imei = "";
char iccid[22];
char port[ ]="80";

String post_data_0="<eMessage ><De mn=\"";
String post_data_1="\" sn=\"";
String post_data_2="\" ow=\"drm-data_source\">";
String post_data_3="<Da><Di n=\"ICCID\" y=\"s\" q=\"g\">";
String post_data_4="</Di></Da></De></eMessage>";

struct config_t {
  char model[mbuf];
  char serial_num[mbuf];
  char username[mbuf];
  char password[mbuf];
  char ipaddress[mbuf];
  char apn[buflen];
}configuration;

int onModulePin = 2;        // the pin to switch on the module (without press on button) 

void switchModule(){
    digitalWrite(onModulePin,HIGH);
    delay(2000);
    digitalWrite(onModulePin,LOW);
}

void setup(){
    Serial.begin(115200);
    Serial1.begin(115200);                // UART baud rate
    delay(2000);

    pinMode(onModulePin, OUTPUT);
    switchModule();                    // switches the module ON
    handleResponse(); 
    
    for (int i=0;i< 5;i++){
        delay(5000);
    } 
    handleResponse();
    Serial1.println("AT+CICCID");
    handleGetICCID();
    
    Serial1.println("AT+CGSN");
    handleGetIMEI();

    imei = String(imeichar);
    
    // only call EEPROM_readanything once per script in setup
    // there is a hard limit on the number of times permanent memory
    // can be written/read
    
    // after this call these variables are available:
    //configuration.model, configuration.serial_num
    // configuration.username, configuration.password
    // configuration.ipaddress, configuration.apn
    EEPROM_readAnything(ee_addr, configuration);

}

void loop(){

   Serial1.print("AT+CGSOCKCONT=1,\"IP\",\"");
   Serial1.print(configuration.apn);
   Serial1.println("\"");
   handleResponse(); 
    
   Serial1.print("AT+CHTTPACT=\""); //Connects with the HTTP server
   Serial1.print(configuration.ipaddress);
   Serial1.print("\",");
   Serial1.println(port);  
   delay(4000); // you need this delay for connecting
   handleResponse();
 
   Serial1.println("POST /eMessage HTTP/1.1");
   handleResponse();
 
   Serial1.print("Host:");
   Serial1.println(configuration.ipaddress);
   handleResponse();
   Serial1.println("User-Agent:ArduinoLeonardo");
   handleResponse();
   String acceptHeader = "Accept:text/html,application/json,application/xhtml+xml,application/xml";
   assembleRequest(acceptHeader);
   Serial1.println("");
   handleResponse();

   Serial1.println("Content-Type:application/xml");
   handleResponse();
   Serial1.print("Content-Length:"); // content length REALLY matters - must be accurate or slightly overestimated (not too much)
   int lenplus = post_data_0.length() + post_data_1.length() + strlen(configuration.model) + strlen(configuration.serial_num) 
   + post_data_2.length()+ post_data_3.length() + post_data_4.length() + strlen(iccid) + 3;
   Serial1.println(String(lenplus));
   
    Serial1.write(0x0D);
    Serial1.write(0x0A);
    Serial1.write(0x0D);
    Serial1.write(0x0A);
    delay(5000);  // you need delays before and after
    assembleRequest(post_data_0);
    
    Serial1.print(configuration.model);
    Serial.print(configuration.model);
    assembleRequest(post_data_1);
    Serial1.print(configuration.serial_num);
    Serial.print(configuration.serial_num);
    assembleRequest(post_data_2);
    assembleRequest(post_data_3);
    Serial1.print(iccid);
    Serial.print(iccid);
    assembleRequest(post_data_4);
    
    Serial1.println("");
    delay(5000);
    Serial1.write(0x0D);
    Serial1.write(0x0A);
     Serial1.write(0x0D);
    Serial1.write(0x0A);
   Serial1.write(0x1A);       //sends ++
   Serial1.write(0x0D);
    Serial1.write(0x0A);

   handleResponse();
   Serial1.flush();
  
  Serial.println(F("End Script"));
  while(1);
  
  
}

void assembleRequest(String longstuff){
  // needs a separate println after function is called
  int postsize = longstuff.length();
  
  if (postsize > 63){
    int times = ceil(postsize / 63);
    int iter=0;
    
    for (iter=0; iter <times; iter+=63){
      Serial1.print(longstuff.substring(iter,iter+63));
      Serial.println(longstuff.substring(iter,iter+63));
      delay(100);
    }
    Serial1.print(longstuff.substring(iter,postsize));
    Serial.println(longstuff.substring(iter,postsize));
  }
  else {
   Serial1.print(longstuff);
    Serial.println(longstuff); 
  }
  
}

void handleResponse(){
  handleResponse(1000);
}

void handleResponse(int delay_length){  
   char serBuf[128]={};
  
  int avail = Serial1.available();
  Serial.println(String(avail));
 while(Serial1.available()>0)
    {
     delay(100);
     Serial1.readBytes(serBuf, 128);
     Serial.println(serBuf);

    } 
  delay(delay_length);
  
}

void handleGetIMEI(){  
   char serBuf[32]={};
  Serial.println(F("Getting IMEI"));
  int avail = Serial1.available();
  Serial.println(String(avail));
  
  Serial1.readBytes(serBuf, 32);
  Serial.println(F("Setting IMEI"));
 for (int i=0;i < 16; i++)
    {
     delay(100);
     imeichar[i]=serBuf[i+9];
    }
   imeichar[16]='\0';
  Serial.println(imeichar);
  Serial1.flush();
  
}

void handleGetICCID(){  
   char serBuf1[64]={};

  Serial.println(F("Getting ICCID"));
  int avail = Serial1.available();
  Serial.println(String(avail));
  if (avail < 15){
     delay(100);
    avail = Serial1.available(); 
  }
  Serial1.readBytes(serBuf1, 64);
  Serial.println(serBuf1);
  Serial.println(F("Setting ICCID"));
 for (int i=0;i < 20; i++)
    {
     delay(100);
     iccid[i]=serBuf1[i+20];
    } 
    iccid[21]='\0';
    Serial.println(iccid);
  
}

