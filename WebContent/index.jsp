<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>AppVoyage</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">

	<!--link rel="stylesheet/less" href="less/bootstrap.less" type="text/css" /-->
	<!--link rel="stylesheet/less" href="less/responsive.less" type="text/css" /-->
	<!--script src="js/less-1.3.3.min.js"></script-->
	<!--append ‘#!watch’ to the browser URL, then refresh the page. -->
	
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/style.css" rel="stylesheet">
	<link href="jasny-bootstrap/css/jasny-bootstrap.min.css" rel="stylesheet">

  <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
  <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
  <![endif]-->

  <!-- Fav and touch icons -->
  <link rel="apple-touch-icon-precomposed" sizes="144x144" href="img/apple-touch-icon-144-precomposed.png">
  <link rel="apple-touch-icon-precomposed" sizes="114x114" href="img/apple-touch-icon-114-precomposed.png">
  <link rel="apple-touch-icon-precomposed" sizes="72x72" href="img/apple-touch-icon-72-precomposed.png">
  <link rel="apple-touch-icon-precomposed" href="img/apple-touch-icon-57-precomposed.png">
  <link rel="shortcut icon" href="img/favicon.png">
  	<link type="text/css" rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500">
    <script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/scripts.js"></script>
	<script type="text/javascript" src="jasny-bootstrap/js/jasny-bootstrap.min.js"></script>
	<script type="text/javascript" src="holder/holder.js"></script>

	
        
        <script type="text/javascript">
        var contextPath = "<%=request.getContextPath()%>";

		$(document).ready(function() {
        
			var userSelection = "";
			
        	$("#call-api").click(function() {
        			$('#loading1').show();	
        			/* $("#call-api").addClass("disabled"); */
        		var fifthParam = "";
        	    	var response="failed";
        	    	var url = contextPath +"/callapi";
        	    	var devKey = document.getElementById("dev-key").value;
        	    	var email = document.getElementById("dev-id").value;
        	    	var merchant = document.getElementById("merchant-name").value;
        	    	var orderId = document.getElementById("order-number").value;
        	    	if(userSelection === "PRINT")
        	    		fifthParam = document.getElementById("order-url").value;
        	    	if(userSelection === "MESSAGE")
 	       	    	fifthParam = document.getElementById("message").value;
        	    	
        	    	var filter=/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
        	    	if (!filter.test(email)){
        	    		alert("Please provide a valid development email!");
        	    		$("#save-button").removeClass("disabled");
        	    		$('#loading1').hide();
        	    		return;
        	    	}
        	    
        	    	var params = {
        	    		devKey:devKey,
        	    		email:email,
        	    		merchant: merchant,
        	    	  email:email,
        	    	  orderId:orderId,
        	    	  fifthParam: fifthParam,
        	    	  api: userSelection
        	    	};
        	    		
        	    	var request = $.ajax({
        	    	  type: "POST",
        	    	  url: url,
        	    	  processData: true,
        	    	  data:params,
        	    	  dataType: 'json', 
        	    	  async: false,
        	    	  success:function(data) {
        	    		  if(data.STATUS == "SUCCESS") {
        	    				alert(data.MESSAGE);
        	    				$('#loading1').hide();
        	    				document.getElementById('textarea-response').style.display="block";
        	    				document.getElementById('textarea-response').value = JSON.stringify(data.RESPONSE);
        	    		  } else {
        	    			  $('#loading1').hide();
            	    		  alert(data.MESSAGE);
        	    		  }
        	    	  },
        	    	  error:function(data) {
        	    	   		  $('#loading1').hide();
            	    		  alert(data.MESSAGE);
            	    		   		
            	    		  }
        	    	});
        	    	//return response;		
        	    });
        
        	$('#print-button').click(function() {
        		document.getElementById('order-url-form').style.display="block";
        		document.getElementById('call-api').style.display="block";
        		document.getElementById('message-form').style.display="none";
        		document.getElementById('print-button').style.display="none";
        		document.getElementById('message-button').style.display="none";
        		userSelection = "PRINT";
        	});	
        	
        
        	$('#message-button').click(function() {
        		document.getElementById('message-form').style.display="block";
        		document.getElementById('call-api').style.display="block";
        		document.getElementById('order-url-form').style.display="none";
        		document.getElementById('print-button').style.display="none";
        		document.getElementById('message-button').style.display="none";
        		userSelection = "MESSAGE";
        	});	
        	
        
        	    });

        </script>
	
</head>

<body>
<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column well">
				<div class="form-group">
					 <label for="order-number">Order Number</label><input type="text" class="form-control" id="order-number"/>
				</div>
				<div class="form-group">
                     <label for="dev-key">Development Key</label><input type="text" class="form-control" id="dev-key"/>
				</div>
				<div class="form-group">
					 <label for="dev-id">Development email</label><input type="email" class="form-control" id="dev-id"/>
				 </div>
				<div class="form-group">
					 <label for="merchant-name">Merchant Name</label><input type="text" class="form-control" id="merchant-name"/>
				 </div>
				 <div id="message-form" class="form-group" style="display: none;">
					 <label for="message">Message</label><input type="text" class="form-control" id="message"/>
				 </div>
				 <div id="order-url-form" class="form-group" style="display: none;">
					 <label for="order-url">Order URL</label><input type="text" class="form-control" id="order-url"/>
				 </div>
					<div class="row"></div>
				  <button type="button" id="print-button" class="btn btn-primary" >Print API</button>
				  <button type="button" id="message-button" class="btn btn-primary" >Message API</button>
				
				<div class="row">&nbsp;</div>
				<div class="row">&nbsp;</div>
				<button type="button" id="call-api" class="btn btn-primary" >Check API<img src="img/ajax-loader.gif" id="loading1" style="display: none;" /></button>
				
				<div class="row"></div>
				<div class="row"></div>
				<textarea readonly id="textarea-response" style="display: none;">
				</textarea>
			
		</div>
	</div>
</div>
   

</body>
</html>
