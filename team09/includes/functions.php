<?php
if (!defined('SECURITY')) error(403, __FILE__, __LINE__);

function boolval($var){
	if(($var === TRUE) OR ($var === 1) OR ($var === '1') OR (strtolower($var) === 'true') OR (strtolower($var) === 'on') OR (strtolower($var) === 'yes') OR (strtolower($var) === 'y')) return TRUE;
	else return FALSE;
}

function var_dump_table($var, $title = FALSE, $level = 0){
	if($level === 0){
		echo "\t".'<table width="400" border="0" cellspacing="1" cellpadding="3" class="dump">'."\r\n";

		if($title) echo "\t\t".'<tr>'."\r\n\t\t\t".'<th align="center" colspan="2">'.$title.'</th>'."\r\n\t\t".'</tr>'."\r\n";

		echo "\t\t".'<tr>'."\r\n\t\t\t".'<th align="right">VAR NAME</th>'."\r\n\t\t\t".'<th align="left">VALUE</th>'."\r\n\t\t".'</tr>'."\r\n";
	}

	foreach($var as $key => $value){
		if(is_array($value) or is_object($value)) var_dump_table($value, FALSE, ($level + 1));
		else echo "\t\t".'<tr>'."\r\n\t\t\t".'<td align="right" width="50%" >'.$key.'</td>'."\r\n\t\t\t".'<td align="left" width="50%" >'.$value.'</th>'."\r\n\t\t".'</tr>'."\r\n";
	}
	echo "\t".'</table>'."\r\n";
}
function error($num = NULL, $file = NULL, $line = NULL){
	global $debug, $db, $ss, $visitor, $page, $iuv;

	$num = (intval($num) === 0) ? 999 : intval($num);
	$err = array(
		400 => array('Bad Request', '<p>Your browser sent a request that this server could not understand.</p>'),
		401 => array('Authorization Required', '<p>This server could not verify that you are authorized to access the document requested. Either you supplied the wrong credentials (e.g., bad password), or your browser doesn\'t understand how to supply the credentials required</p>'),
		402 => array('Payment Required', '<p>The server encountered an internal error or misconfiguration and was unable to complete your request.</p>'."\r\n".'<p>Please contact the server administrator, and inform them of the time the error occurred, and anything you might have done that may have caused the error.</p>'."\r\n".'<p>More information about this error may be available in the server error log.</p>'),
		403 => array('Forbidden', '<p>You don\'t have permission to access '.$_SERVER['REQUEST_URI'].' on this server.</p>'),
		404 => array('Not Found', '<p>The requested URL '.$_SERVER['REQUEST_URI'].' was not found on this server.</p>'),
		405 => array('Method Not Allowed', '<p>The requested method POST is not allowed for the URL '.$file.'</p>'."\r\n".'<p>This error was most likely caused by being directed to this page from an external URL that was trying to send data.</p>'."\r\n".'<p>To solve this problem, please click <a href="'.URL.'">here</a>.</p>'),
		406 => array('Not Acceptable', '<p>An appropriate representation of the requested resource '.$file.' could not be found on this server.</p>'),
		407 => array('Proxy Authentication Required', '<p>This server could not verify that you are authorized to access the document requested.  Either you supplied the wrong credentials (e.g., bad password), or your browser doesn\'t understand how to supply the credentials required.</p>'),
		408 => array('HTTP Timeout', '<p>Server timeout waiting for the HTTP request from the client.</p>'),
		409 => array('Conflict', '<p>The server encountered an internal error or misconfiguration and was unable to complete your request.</p>'."\r\n".'<p>Please contact the server administrator, admin@develogix.com and inform them of the time the error occurred, and anything you might have done that may have caused the error.</p>'."\r\n".'<p>More information about this error may be available in the server error log.</p>'),
		410 => array('Gone', '<p>The requested resource "'.$file.'" is no longer available on this server and there is no forwarding address. Please remove all references to this resource.</p>'),
		411 => array('Length Required', '<p>A request of the requested method GET requires a valid Content-length.</p>'),
		412 => array('Precondition Failed', '<p>The precondition on the request for the URL "'.$_SERVER['REQUEST_URI'].'" evaluated to false.</p>'),
		413 => array('Request Entity Too Large', '<p>The requested resource "'.$_SERVER['REQUEST_URI'].'" does not allow request data with GET requests, or the amount of data provided in the request exceeds the capacity limit.</p>'),
		414 => array('Request URI Too Large', '<p>The requested URL\'s length exceeds the capacity limit for this server.</p>'),
		415 => array('Unsupported Media Type', '<p>The supplied request data is not in a format acceptable for processing by this resource.</p>'),
		500 => array('Internal Server Error', '<p>The server encountered an internal error or misconfiguration and was unable to complete your request.</p>'."\r\n".'<p>Please contact the server administrator, admin@develogix.com and inform them of the time the error occurred, and anything you might have done that may have caused the error.</p>'."\r\n".'<p>More information about this error may be available in the server error log.</p>'),
		501 => array('Method Not Implemented', '<p>GET to "'.$_SERVER['REQUEST_URI'].'" not supported.</p>'),
		503 => array('Service Temporarily Unavailable', '<p>The server is temporarily unable to service your request due to maintenance downtime or capacity problems. Please try again later.</p>'),
		999 => array('', '')
	);
	
	switch($num){
		case 400 :
		case 401 :
		case 402 :
		case 403 :
		case 404 :
		case 405 :
		case 406 :
		case 407 :
		case 408 :
		case 409 :
		case 410 :
		case 411 :
		case 412 :
		case 413 :
		case 414 :
		case 415 :
		case 500 :
		case 501 :
		case 503 :
		case 999 :
			$page->setTitle('Error '.$num);
			$page->header();

			echo '<h1>'.$num.' '.$err[$num][0].'</h1>'."\r\n";
			echo $err[$num][1]."\r\n";
			if(DEBUG) echo '<h2>File: '.$file.' - Line: '.$line.'<br />'."\r\n";
						
			$page->footer();
			die();
		break;
		default :
			echo '';
		break;
	}
}
function error_json($desc = NULL, $file = NULL, $line = NULL){
	echo json_encode(array('error' => 'true', 'text' => $desc));
	die();
}
function output_json($data = array()){
	echo json_encode($data);
	die();
}
?>