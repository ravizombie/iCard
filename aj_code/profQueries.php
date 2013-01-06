<?php
	$jsonOutput = endClassAttendance($_GET["courseid"],$_GET["date"]);
	print_r ($jsonOutput);
	

	

	function openDB(){
		include('database.php');
		  if($conn = mysql_connect($MYSQL_Server, $MYSQL_User, $MYSQL_Password)){
			  return $conn;
		  }else{
			  echo 'MYSQL CONN ERROR: '.mysql_error();
			  return false;
		  }
	}
	
	

	//PROFESSOR FUNCTIONS
	function endClassAttendance($courseid,$lectureDate){
		include('database.php');
		$conn = openDB();
		if(!$conn){
			echo 'open db fail';
		}else{
			$count = 0;
			mysql_select_db($MYSQL_DB, $conn);
			$query = "Select * from checkin where courseid='".$courseid."'and DATE(intime)='".$lectureDate."'";
			$result = mysql_query($query);
				$jsonOutput = "{students:[";
			

			while($row = mysql_fetch_array($result))
			  {
				if($count >=1) {
					$jsonOutput.=",";				
				}
				$jsonOutput.="{";
				$jsonOutput.="\"studentId\":"."\"".$row['studentid']."\",";
				$jsonOutput.="\"status\":"."\"".$row['status']."\",";
				$jsonOutput.="\"duration\":"."\"".$row['duration']."\",";
				$query1 = "select * from student where id =".$row['studentid'];
				$result1 = mysql_query($query1);
				$row1 = mysql_fetch_array($result1);
				$jsonOutput.="\"studentName\":"."\"".$row1['name']."\"";
				$jsonOutput.="}";
				$count++;
			  }
			$jsonOutput = $jsonOutput."]}";

			closeDB($conn);
			return $jsonOutput;
		}
	}
	
		
	function closeDB($conn){
		mysql_close($conn);
	}
?>