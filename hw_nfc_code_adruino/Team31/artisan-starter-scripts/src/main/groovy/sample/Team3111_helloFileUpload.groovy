import com.axeda.services.v2.FileInfo
import com.axeda.sdk.v2.bridge.FileInfoBridge

/****************
 * Hello File Upload!
 *
 * 11_helloFileUpload.groovy
 *
 * Creating a File Upload works with a Bridge.
 *
 * **************/

FileInfo file = new FileInfo();
file.filename = parameters.fileName
file.filesize = Long.parseLong(parameters.fileSize)

FileInfoBridge fileUploadBridge = bridges.getFileInfoBridge();
return['Content-Type':'text/xml', 'Content': fileUploadBridge.create(file)]
