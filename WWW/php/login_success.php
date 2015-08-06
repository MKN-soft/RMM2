<?php
session_start();
if(!isset($_SESSION['username'])){
header("location:index.php");
}


require_once 'FunctionsDB.php';
$db = new FunctionsDB();

$result = $db->getStatistics($_SESSION['username']);

echo "Uzytkownik ".$_SESSION['username'];
echo "<br>";

 
while($row = mysql_fetch_object($result)){
	echo "<h1>data wprowadzenia: ".$row->updated_at."</h1>";
	echo "<b>sredni czas: </b>".($row->time / 1000)." sekund";
	echo "<br>";
	echo "<b>srednia wielkosc planszy: </b>".$row->size."x".$row->size;
	echo "<br>";
	echo "<b>srednia ilosc ruchow: </b>".$row->movements;
	echo "<br>";
	echo "<br>";
	echo "<br>";
}



?>

<html>
<body>
</body>
</html>