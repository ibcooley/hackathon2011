<?php
if (!defined('SECURITY')){
	include_once(HOME_DIR.DIRECTORY_SEPARATOR.'includes'.DIRECTORY_SEPARATOR.'functions.php');
	error(403, __FILE__, __LINE__);
}

if(($page->getQueryCount() === 1) && (strtolower($page->getQuery(0)) === 'api')) header('Location: /');
else if(($page->getQueryCount() === 2) && (strtolower($page->getQuery(0)) === 'api') && (strtolower($page->getQuery(1)) === 'register')){
	$visitor = new Visitor();
	$visitor->register($_POST);
}
else if(($page->getQueryCount() === 2) && (strtolower($page->getQuery(0)) === 'api') && (strtolower($page->getQuery(1)) === 'login')){
	$visitor = new Visitor();
	$visitor->login($_POST);
}
else if(($page->getQueryCount() === 2) && (strtolower($page->getQuery(0)) === 'api') && (strtolower($page->getQuery(1)) === 'get')) header('Location: /');
else if(($page->getQueryCount() === 3) && (strtolower($page->getQuery(0)) === 'api') && (strtolower($page->getQuery(1)) === 'get') && (strtolower($page->getQuery(2)) === 'song')) header('Location: /');
else if(($page->getQueryCount() === 4) && (strtolower($page->getQuery(0)) === 'api') && (strtolower($page->getQuery(1)) === 'get') && (strtolower($page->getQuery(2)) === 'song')){
	// search for artist
	$results = $db->query('SELECT `id`, `name` FROM `song_artists` WHERE `name` LIKE(\'%'.urldecode($page->getQuery(3)).'%\')');
	$output  = array();
	while($row = $db->fetch_array($results)){
		$output[] = array('id' => $row['id'], 'name' => $row['name']);
	}
	output_json($output);
}
else if(($page->getQueryCount() === 5) && (strtolower($page->getQuery(0)) === 'api') && (strtolower($page->getQuery(1)) === 'get') && (strtolower($page->getQuery(2)) === 'song')){
	$results = $db->query('SELECT `id`, `title` FROM `songs` WHERE `artist_id` = \'(SELECT `id` FROM `song_artists` WHERE `name` = \''.mysql_real_escape_string(urldecode($page->getQuery(3))).'\')\' ORDER BY `name`;');
	$output  = array();
	while($row = $db->fetch_array($results)){
		$output[] = array('id' => $row['id'], 'title' => $row['title']);
	}
	output_json($output);
}
?>