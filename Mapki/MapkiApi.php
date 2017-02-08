<?php
  require_once 'Utils.php';
  require_once 'User.php';
  session_start();

  $method = $_SERVER['REQUEST_METHOD'];
  switch($method){
    case 'POST':
      $request = getRequestType($_SERVER['REQUEST_URI']);
      switch($request){
        case 'login':
          $json = login();
          echo(json_encode($json));
          break;
        case 'logout':
          $json = logout();
          echo(json_encode($json));
          break;
        case 'addLocation':
          $json = addLocation();
          echo(json_encode($json));
          break;
        case 'getOwnedLocations':
          $json = getOwnedLocations();
          echo(json_encode($json));
          break;
        case 'deleteLocation':
          $json = deleteLocation();
          echo(json_encode($json));
          break;
      }
      break;
  }



  function login(){
    if(isset($_SESSION['user'])){
      $response['success'] = "You are already logged, ".$_SESSION['user']->getUserLogin();
      return $response;
    }
    if(isset($_POST['login'],$_POST['password']) ){
      if(strlen($_POST['login'])<4){
        $response['error'] = "Login must have 4 or more letters";
      }
      elseif (strlen($_POST['password'])<8) {
        $response['error'] = "Pasword must have 8 or more letters";
      }
      else{
        $user = new User($_POST['login'],$_POST['password']);
        $check = $user->checkUser();
        if(isset($check['error'])){
          $response = $check;
        }
        else{
          $response = $check;
          $_SESSION['user'] = $user;
        }
      }
    }
    else{
      $response['error'] = "Set login and password";
    }
    return $response;
  }

  function logout(){
    if(isset($_SESSION['user'])){
      $response['success'] = "You are logged off, ".$_SESSION['user']->getUserLogin();
      unset($_SESSION['user']);
    }
    else{
      $response['error'] = "Nobody was logged";
    }
    return $response;
  }

  function addLocation(){
    if(isset($_SESSION['user'])){
      if(isset($_POST['description'],$_POST['coord_x'],$_POST['coord_y'],$_POST['private'])){
        $userId = $_SESSION['user']->getId();
        $response['success'] = addLocationDB($userId,$_POST['coord_x'],$_POST['coord_y'],$_POST['description'],$_POST['private']);
      }
      else{
        $response['error'] = "Set all params";
      }
    }
    else{
      $response['error'] = "You are not logged on";
    }
    return $response;
  }

  function getOwnedLocations(){
    if(isset($_SESSION['user'])){
      $response['success'] = "Your locations updated";
      $response['locations'] = getPlayersLocationsDB($_SESSION['user']->getId());
    }
    else{
      $response['error'] = "You are not logged on";
    }
    return $response;
  }

  function deleteLocation(){
    if(isset($_SESSION['user'])){
      if(isset($_POST['location_id'])){
        $location = getLocationDB($_POST['location_id']);
        if($location == "No such location"){
          $response['error'] = $location;
        }
        else{
          if($location['owner_id'] == $_SESSION['user']->getId() ){
            deleteLocationDB($_POST['location_id']);
            $response['success'] = "Location deleted";
          }
          else{
            $response['error'] = "You are not owner";
          }
        }
      }
    }
    else{
      $response['error'] = "You are not logged on";
    }
    return $response;
  }
?>
