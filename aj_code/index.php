<?php

$_GET=['q'];


$arr = array(
    array(
        "name" => "ajinkya",
        "status" => "inactive"
    ),
    array(
        "name" => "ravi",
        "status" => "active"
    ),
    array(
        "name" => "efrain",
        "status" => "active"
    )
	
	array(
        "name" => "john",
        "status" => "active"
    )
);


echo json_encode($arr);






?>