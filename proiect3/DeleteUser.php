<?php

require "conn.php";

if($_SERVER['REQUEST_METHOD']=='POST'){

 $ID = $_POST['UserID'];

$Sql_Query = "DELETE FROM users_table WHERE id = '$ID'";

 if(mysqli_query($conn,$Sql_Query))
{
 echo 'Record Deleted Successfully';
}
else
{
 echo 'Something went wrong';
 }
 }
 mysqli_close($conn);
?>