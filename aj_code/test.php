<?php
include('database.php');
if($conn = mysql_connect($MYSQL_Server, $MYSQL_User, $MYSQL_Password)){
	 echo "Database connection successful";
	 return $conn;
	
}else{
	 echo 'MYSQL CONN ERROR: '.mysql_error();
	return false;
		  }
?>
