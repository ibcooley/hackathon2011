<?php
if (!defined('SECURITY')){
	include_once(HOME_DIR.DIRECTORY_SEPARATOR.'includes'.DIRECTORY_SEPARATOR.'functions.php');
	error(403, __FILE__, __LINE__);
}

if(($visitor !== NULL) && ($visitor->isLoggedIn())){
	if($page->getQuery(0) && ($page->getQuery(1) === 'dj')){
		if($page->getQuery(1) && (strlen($page->getQuery(1)) >= 3) && (strlen($page->getQuery(1) <= 32))){
			if($page->getQueryCount() === 2){
				$page->addMeta('js', '/js/form.js');
				$page->addMeta('js', '/js/dj.js');
				$page->header();
				
				// Is DJ favorited?
				$result = $db->query('SELECT COUNT(*) FROM `user_favorites` WHERE `user_id` = \'(SELECT `user_id` FROM `users` WHERE `username` = \''.$session->get('username').'\')\' LIMIT 1;');
				if($db->num_rows($result) === 1) $favorited = TRUE;
				else $favorited = FALSE;
				
				// About DJ
				$aboutDJ = NULL;
				$result = $db->query('SELECT `about` FROM `user_about` WHERE `user_id` = \'(SELECT `user_id` FROM `users` WHERE `username` = \''.mysql_real_escape_string($page->getQuery(1)).'\')\' LIMIT 1;');
				if($db->num_rows($result) === 1){
					$row = $db->fetch_array($result);
					
					$aboutDJ = array(
						'username' => $page->getQuery(1),
						'text'     => $row['text']
					);
				}
				
				// Now Playing
				$result = $db->query('SELECT s.artist AS `artist`, s.title AS `title`, rv.COUNT(*) AS `count`, r.id AS `id` FROM `songs` s, `club` c, `request` r, `request_votes` rv WHERE c.dj_id = \''.mysql_real_escape_string($page->getQuery(1)).'\' AND c.currently_playing = \'true\' AND c.currently_playing_id = s.id AND r.song_id = s.id AND rv.request_id = r.id LIMIT 1;');
				if($db->num_rows($result) === 1){
					$row = $db->fetch_array($result);
					
					$isCurrentlyPlaying = array(
						'count'  => $row['count'],
						'artist' => $row['artist'],
						'title'  => $row['title']
					);
					
					$result2 = $db->query('SELECT u.username AS `username` FROM `request_votes` rv, `users` u WHERE rv.request_id = \''.$row['id'].'\' AND rv.user_id = u.id');
					if($db->num_rows($result2) > 0){
						while($row2 = $db->fetch_array($result2)){
							$currentlyPlayingRequestedBy[] = $row2['username'];
						}
					}
					else $currentlyPlayingRequestedBy = FALSE;
				}
				else $isCurrentlyPlaying = FALSE;
				
				// Top Requests
				$topRequests = array();
				
				$result = $db->query('SELECT rv.COUNT(*) AS `request_count`, sa.artist AS `song_artist`, s.title as `song_title`, r.id as `request_id` FROM `request_votes` rv, `requests` r, `songs` s, `song_artists` sa WHERE `dj_id` = (SELECT `user_id` FROM `users` WHERE `username` = \''.mysql_real_escape_string($page->getQuery(1)).'\') AND r.song_id = s.id AND s.artist_id = sa.id ORDER BY COUNT DESC LIMIT 10;');
				while($row = $db->fetch_array($result)){
					$result2 = $db->query('SELECT * FROM `request_votes` WHERE `user_id` = (SELECT `user_id` FROM `users` WHERE `username` = \''.$session->get('username').'\') LIMIT 1;');
					$topRequests[] = array(
						'id'        => $row['id'],
						'count'     => $row['request_count'],
						'artist'    => $row['song_artist'],
						'title'     => $row['song_title'],
						'requested' => (($db->num_rows($result2) === 0) ? FALSE : TRUE)
					);
				}
				
?>
		<section id="dj">
			<section id="djMeta">
				<section id="dj_name">
				<section id="dj_favorite"><span class="ui-state-default ui-corner-all"><a href="<?php echo $page->getURL().'/favorite'; ?>" class="ui-icon ui-icon-<?php echo (($favorited) ? 'minus' : 'plus'); ?>thick">Favorite</a></span><br />
			</section>
<?php
if($aboutDJ !== NULL){
?>
			<section id="djAbout">
				<fieldset id="djAboutFieldset">
					<legend>About <?php echo $aboutDJ['username']; ?></legend>
					<p>
						<?php echo str_replace("\n", "\n\t\t\t\t\t\t", $aboutDJ['text'])."\r\n"; ?>
					</p>
				</fieldset>
			</section>
<?php
}
if(is_array($isCurrentlyPlaying)){
?>
			<section id="djCurrentlyPlaying">
				<fieldset id="djCurrentlyPlayingFieldset">
					<legend>Now Playing</legend>
					<p>
<?php
echo "\t\t\t\t\t\t".$isCurrentlyPlaying['artist'].' - '.$isCurrentlyPlaying['title']."\r\n";
echo "\t\t\t\t\t\t".'with '.$isCurrentlyPlaying['count'].' requests.';
?>
					</p>
<?php
if(is_array($currentlyPlayingRequestedBy)){
	echo "\t\t\t\t\t".'<section id="djCurrentlyPlayingRequestedBy">'."\r\n";
	echo "\t\t\t\t\t\t".'<ul>'."\r\n";
	foreach($currentlyPlayingRequestedBy AS $request){
		echo "\t\t\t\t\t\t\t".'<li>'.$request['username'].'</li>'."\r\n";
	}
	echo "\t\t\t\t\t\t".'<ul>'."\r\n";
	echo "\t\t\t\t\t".'</section>'."\r\n";
}
?>
					</section>
				</fieldset>
			</section>
<?php
}
?>
		</section>
		<section id="request">
			<fieldset id="requestFieldset">
				<legend>Request a Song</legend>
				<form id="requestForm">
					<label for="artist">Artist:</label><br />
					<input type="text" placeholder="Artist" id="artist" name="artist"><br />
					<label for="song">Song Title:</label><br />
					<input type="text" placeholder="Song Title" id="song_title" name="song_title" /><br />
					<input type="submit" value="Request" id="requestSubmit" name="requestSubmit" />
				</form>
			</fieldset>
		</section>
		<section id="topRequests">
			<ul>
<?php
foreach($topRequests AS $request){
	echo "\t\t\t\t".'<li>'.$request['count'].' - '.$request['artist'].' - '.$request['title'].' <span class="ui-state-default ui-corner-all" title="Favorite"><a href="/favorite" class="ui-icon ui-icon-'.(($request['requested']) ? 'minus' : 'plus').'thick"></a></span></li>'."\r\n;";
}
?>
			</ul>
		</section>
<?php
				$page->footer();
			}
			else if(($page->getQueryCount() === 3) && ($page->getQuery(2) && (strtolower($page->getQuery(2)) === 'favorite')){
				if($db->query('INSERT INTO'))
			}
			else error(404, __FILE__, __LINE__);
		}
	}
	else error(404, __FILE__, __LINE__);
}
else header('Location: /login');
?>