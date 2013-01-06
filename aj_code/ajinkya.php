<?php
$string = file_get_contents("https://atthackathon-sandbox.axeda.com/services/v1/rest/Scripto/execute/Team31YourApp?username=team31user&password=team31pass");
$json = json_decode($string, true);
$i=0;
foreach($json as $item) {

	$a=$item['lastContact'];
	
    echo $a;
}


?>