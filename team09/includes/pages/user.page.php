<?php
if (!defined('SECURITY')){
	include_once(HOME_DIR.DIRECTORY_SEPARATOR.'includes'.DIRECTORY_SEPARATOR.'functions.php');
	error(403, __FILE__, __LINE__);
}

$page->header();

// list favorites
echo "\t".'<section id="userFavorites">'."\r\n";
$results = $db->query('SELECT u.username FROM `users`, u `user_favorites` uf WHERE uf.user_id = \'(SELECT `user_id` FROM `users` WHERE `username` = \''.$visitor->username.'\')\' AND u.id = uf.dj_id');
if($db->num_rows($results) > 0){
	echo "\t\t".'<ul>'."\r\n";
	while($row = $db->fetch_array($results)){
		echo "\t\t\t".'<li><a href="/dj/'.$row['username'].'">'.$row['username'].'</a></li>'."\r\n";
	}
	echo "\t\t".'</ul>'."\r\n";
}
echo "\t".'</section>'."\r\n";

$page->footer();
?>