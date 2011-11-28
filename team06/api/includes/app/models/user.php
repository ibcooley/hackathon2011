<?php

namespace Simplevent\Models;

/**
 * Someone who is registered with the system
 */
class User extends Person
{
    /**
     * @var array
     */
    private $comment_ids = array();

    /**
     * @var array
     */
    private $event_ids = array();

    /**
     * @var array
     */
    private $requested_item_ids = array();

    /**
     * @var array
     */
    private $claimed_item_ids = array();

    /**
     * array('user_id' => 0, 'is_pending' => 0)
     *
     * @var array
     */
    private $user_ids = array();

    const PENDING_FRIEND_REQUEST = 1;

    /**
     * @param $id
     */
    public function __construct($id)
    {
        $db = \Walleye\Database::getInstance();

        // get user info
        $get_user_stmt = $db->prepare('SELECT display_name FROM Users WHERE id = ?');
        $get_user_stmt->bind_param('i', $id);
        $get_user_stmt->execute();

        $user_row = $db->getRow($get_user_stmt);
        $this->display_name = $user_row->display_name;

        // get user events
        $get_user_events_stmt = $db->prepare('SELECT event_id FROM EventUsers WHERE user_id = ?');
        $get_user_events_stmt->bind_param('i', $id);
        $get_user_events_stmt->execute();

        $user_events_result = $db->getResult($get_user_events_stmt);
        foreach ($user_events_result as $user_event_row) {
            $this->event_ids[] = $user_event_row->event_id;
        }

        // get user users
        $get_user_users_stmt = $db->prepare('SELECT recipient_user_id, is_pending FROM UserUsers WHERE requested_by_user_id = ?');
        $get_user_users_stmt->bind_param('i', $id);
        $get_user_users_stmt->execute();

        $user_users_result = $db->getResult($get_user_users_stmt);
        foreach ($user_users_result as $user_user_row) {
            $this->user_ids[] = array(
                'user_id' => $user_user_row->recipient_user_id,
                'is_pending' => $user_user_row->is_pending
            );
        }

        // get user comments
        $get_user_comments_stmt = $db->prepare('SELECT id FROM Comments WHERE created_by_user_id = ?');
        $get_user_comments_stmt->bind_param('i', $id);
        $get_user_comments_stmt->execute();

        $user_comments_result = $db->getResult($get_user_comments_stmt);
        foreach ($user_comments_result as $user_comment_row) {
            $this->comment_ids[] = $user_comment_row->id;
        }

        // get user requested items
        $get_user_requested_items_stmt = $db->prepare('SELECT id FROM Items WHERE created_by_user_id = ?');
        $get_user_requested_items_stmt->bind_param('i', $id);
        $get_user_requested_items_stmt->execute();

        $user_requested_items_result = $db->getResult($get_user_requested_items_stmt);
        foreach ($user_requested_items_result as $user_requested_item_row) {
            $this->requested_item_ids[] = $user_requested_item_row->id;
        }

        // get user claimed items
        $get_user_claimed_items_stmt = $db->prepare('SELECT id FROM Items WHERE claimed_by_user_id = ?');
        $get_user_claimed_items_stmt->bind_param('i', $id);
        $get_user_claimed_items_stmt->execute();

        $user_claimed_items_result = $db->getResult($get_user_claimed_items_stmt);
        foreach ($user_claimed_items_result as $user_claimed_item_row) {
            $this->claimed_item_ids[] = $user_claimed_item_row->id;
        }

        parent::__construct($id);
    }

    /**
     * @static
     * @param $id
     * @return null|User
     */
    public static function withId($id)
    {
        if ($id == User::ID_OF_UNKNOWN_USER) {
            return null;
        }
        
        $instance = new User($id);
        if (is_null($instance->getId()) == true) {
            return null;
        }
        return $instance;
    }

    /**
     * @return array
     */
    public function getComments()
    {
        $comments = array();
        foreach ($this->comment_ids as $comment_id) {
            $comments[] = Comment::withId($comment_id);
        }
        return $comments;
    }

    /**
     * @return array
     */
    public function getRequestedItems()
    {
        $requested_items = array();
        foreach ($this->requested_item_ids as $requested_item_id) {
            $requested_items[] = Item::withId($requested_item_id);
        }
        return $requested_items;
    }

    /**
     * @return array
     */
    public function getClaimedItems()
    {
        $claimed_items = array();
        foreach ($this->claimed_item_ids as $claimed_item_id) {
            $claimed_items[] = Item::withId($claimed_item_id);
        }
        return $claimed_items;
    }

    /**
     * @return array
     */
    public function getUsers()
    {
        $users = array();
        foreach ($this->user_ids as $user_id) {
            $users[] = User::withId($user_id);
        }
        return $users;
    }

    public function getEvents()
    {
        $events = array();
        foreach ($this->event_ids as $event_id) {
            $events[] = Event::withId($event_id);
        }
        return $events;
    }

    public static function getAllUsers()
    {
        $db = \Walleye\Database::getInstance();
        $get_users_stmt = $db->prepare('SELECT id FROM Users');
        $get_users_stmt->execute();
        $user_results = $db->getResult($get_users_stmt);
        $users = array();

        foreach ($user_results as $user_row) {
            $users[] = User::withId($user_row->id);
        }
        return $users;
    }

}

/* End of file */
