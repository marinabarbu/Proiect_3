<?php

require "conn.php";

$username = $_POST['username'];
$email = $_POST['email'];
$password = $_POST['psw'];
$mobile = $_POST['mobile'];
$gender = $_POST['gender'];
$rol = 'client';

/*
$username = "admin_user";
$email = "admin_user@yahoo.com";
$password = "admin_user123";
$mobile = "0345345234";
$gender = "Female";
$rol = 'admin';
*/

$isValidEmail = filter_var($email, FILTER_VALIDATE_EMAIL);

if($conn){
if(strlen($password) > 40 || strlen($password) < 6){
echo "Passord must be less than 40 and more then 6 characters";
}else if($isValidEmail === false){
echo "This Email is not valid";	
}
else{
$sqlCheckUsername = "SELECT * FROM `users_table` WHERE `username` LIKE '$username'";
$usernameQuery = mysqli_query($conn,$sqlCheckUsername);

$sqlCheckEmail = "SELECT * FROM `users_table` WHERE `email` LIKE '$email'";
$emailQuery = mysqli_query($conn,$sqlCheckEmail);

if(mysqli_num_rows($usernameQuery) > 0){
echo "User name is already used, type another one";
}else if(mysqli_num_rows($emailQuery) > 0){
echo "This Email is already registered, type another email";
}
else{
$hashed = password_hash($password, PASSWORD_DEFAULT);
$sql_register = "INSERT INTO `users_table` (`username`,`email`,`password`,`mobile`,`gender`, `rol`) VALUES ('$username','$email','$hashed','$mobile','$gender', '$rol')";

if(mysqli_query($conn,$sql_register)){
echo "Successfully Registered";
}
else{
echo "Failed to register";
}
}

}
}
else{
echo "Connection Error";
}
?>