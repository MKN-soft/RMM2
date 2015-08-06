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
    <div id="header"><h1>HEADER</h1></div>
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
      	<a href="?page=statistics">Statistics</a>
  	  </div>
    </div>
  </div>
  <div id="ft">
    <div id="footer">FOOTER</div>
  </div>
</div>
</body>
</html>
