<?php
  require_once 'config.php';


  function getUser($email,$password){
		global $host, $db_user, $db_password, $db_name;
		$db_connect = @new mysqli($host, $db_user, $db_password, $db_name);
		$queryStr = sprintf('SELECT * FROM `users` WHERE email = "%s" AND password = "%s"',$email, $password);
		$result = @$db_connect->query($queryStr);
		$users_number = $result->num_rows;
    if($users_number > 0 ){
      $user = $result->fetch_assoc();
    }
    else{
      $user = "No such user";
    }

		mysqli_close($db_connect);
		return $user;
	}

  function addUser($email,$password){
    global $host, $db_user, $db_password, $db_name;
		$db_connect = @new mysqli($host, $db_user, $db_password, $db_name);
    $queryStr = sprintf('INSERT INTO `users`(`email`, `password`) VALUES ("%s","%s")',$email, $password);
    $result = @$db_connect->query($queryStr);
    if($result){
      $response = "Added new user";
    }
    else{
      $response = "Error";
    }
    return $response;
  }

?>
