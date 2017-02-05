<?php
  require_once 'config.php';


  function getUserDB($email,$password){
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

  function addUserDB($email,$password){
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

  function addLocationDB($owner_id, $x_coord, $y_coord, $description, $private){
    global $host, $db_user, $db_password, $db_name;
		$db_connect = @new mysqli($host, $db_user, $db_password, $db_name);
    $queryStr = sprintf('INSERT INTO `locations`(`owner_id`, `description`, `x_coord`, `y_coord`, `private`) VALUES (%s,"%s",%s,%s,%s)',
    $owner_id, $description, $x_coord, $y_coord, $private);
    $result = @$db_connect->query($queryStr);
    if($result){
      $response = "Added location";
    }
    else{
      $response = "Error";
    }
    return $response;
  }

  function getPlayersLocationsDB($owner_id){
    global $host, $db_user, $db_password, $db_name;
		$db_connect = @new mysqli($host, $db_user, $db_password, $db_name);
		$queryStr = sprintf('SELECT * FROM `locations` WHERE owner_id = %s', $owner_id);
		$result = @$db_connect->query($queryStr);
    $locations = array();

    while (@$row=mysqli_fetch_row($result))
		{
			array_push($locations, $row);
		}
    return $locations;
  }
?>
