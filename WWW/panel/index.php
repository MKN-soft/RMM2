<?php

// include page class
require_once 'Page.php';

$page = new Page();
?>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>RMM - Panel Page</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" >
<link rel="stylesheet" type="text/css" href="../css/panel.css" >
</head>
<body>
<div id="doc" class="yui-t2">
  <div id="hd">
    <div id="header"><h1>Hello User <?=$_SESSION['username']?>!</h1></div>
  </div>
  <div id="bd">
    <div id="yui-main">
      <div class="yui-b">
        <div class="content">
        	<?=$page->get();?>
    	</div>
      </div>
    </div>
    <div class="yui-b">
      <div id="secondary">
      	<a href="?page=">Home</a>
      </br>
      	<!--<a href="?page=habits">Habits</a>-->
      	Habits
  		</br>
      		
      		<?php
      			$habits = $page->getHabits();
      		
				while($row = mysql_fetch_object($habits)){
					echo "&emsp; <a href=\"?page=habits&habit=$row->id\">Nawyk id: '$row->id'</a> ";
				}
      		?>		
      		
      </br>
      	<a href="?page=logout">Log out</a>
  	  </div>
    </div>
  </div>
  <div id="ft">
    <div id="footer">RMM - all rights reserved</div>
  </div>
</div>
</body>
</html>

<?php 
unset($page);
?>