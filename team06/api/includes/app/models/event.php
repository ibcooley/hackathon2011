<?php

namespace Simplevent\Models;

/**
 * An Event
 */
class Event
{
    /**
     * @var int
     */
    private $id;

    /**
     * @var string
     */
    public $title;

    /**
     * @var string
     */
    public $desc;

    /**
     * @var int
     */
    private $created_by_user_id;

    /**
     * @var boolean
     */
    private $is_private;

    /**
     * array('user_id' => 0, 'user_group' => 0)
     *
     * @var array
     */
    private $user_ids_and_groups = array();

    /**
     * @var array
     */
    private $attending_user_ids = array();

    /**
     * @var array
     */
    private $declined_user_ids = array();

    /**
     * @var array
     */
    private $maybe_user_ids = array();

    /**
     * @var array
     */
    private $awaiting_response_user_ids = array();

    /**
     * @var string
     */
    private $longitude;

    /**
     * @var string
     */
    private $latitude;

    /**
     * @var array
     */
    private $item_ids = array();

    /**
     * @var bool
     */
    private $is_busted;

    /**
     * @var bool
     */
    private $is_over;

    /**
     * @var array
     */
    private $comment_ids = array();

    /**
     * @var string
     */
    private $date_created;

    const ID_OF_UNKNOWN_EVENT = 0;

    const BUSTED = 1;

    const OVER = 1;

    const IS_PRIVATE = 1;

    /**
     * User Groups
     */
    const AWAITING_CONFIRMATION = 0;

    const MAYBE = 1;

    const ATTENDING = 2;

    const DECLINED = 3;

    /**
     * @param int $id
     */
    private function __construct($id)
    {
        $db = \Walleye\Database::getInstance();
        $get_event_stmt = $db->prepare('SELECT title, description, is_private, longitude, latitude, is_over, is_busted, date_created FROM Events WHERE id = ?');
        $get_event_stmt->bind_param('i', $id);
        $get_event_stmt->execute();

        if ($event_row = $db->getRow($get_event_stmt)) {
            // event info
            $this->id = $id;
            $this->title = $event_row->title;
            $this->desc = $event_row->description;
            $this->is_private = $event_row->is_private;
            $this->longitude = $event_row->longitude;
            $this->latitude = $event_row->latitude;
            $this->is_over = $event_row->is_over;
            $this->is_busted = $event_row->is_busted;
            $this->date_created = $event_row->date_created;

            // event users
            $get_event_users = $db->prepare('SELECT user_id, user_group FROM EventUsers WHERE event_id = ?');
            $get_event_users->bind_param('i', $id);
            $get_event_users->execute();
            $event_users_result = $db->getResult($get_event_users);
            foreach ($event_users_result as $event_user_row) {
                $this->user_ids_and_groups[] = array(
                    'user_id' => $event_user_row->user_id,
                    'user_group' => $event_user_row->user_group
                );
            }

            // event comments
            $get_event_comments = $db->prepare('SELECT id FROM Comments WHERE event_id = ?');
            $get_event_comments->bind_param('i', $id);
            $get_event_comments->execute();
            $event_comments_result = $db->getResult($get_event_comments);
            foreach ($event_comments_result as $event_comments_row) {
                $this->comment_ids[] = $event_comments_row->id;
            }

            // event items
            $get_event_items = $db->prepare('SELECT id FROM Items WHERE event_id = ?');
            $get_event_items->bind_param('i', $id);
            $get_event_items->execute();
            $event_items_result = $db->getResult($get_event_items);
            foreach ($event_items_result as $event_items_row) {
                $this->item_ids[] = $event_items_row->id;
            }

        }
        else {
            $this->id = Event::ID_OF_UNKNOWN_EVENT;
        }
    }

    /**
     * @static
     * @param $id
     * @return null|Event
     */
    public static function withId($id)
    {
        $instance = new Event($id);
        if ($instance->getId() != Event::ID_OF_UNKNOWN_EVENT) {
            return $instance;
        }
        return null;
    }

    /**
     * @static
     * @param $title
     * @param $longitude
     * @param $latitude
     * @param null $created_by_user_id
     * @return null|Event
     */
    public static function create($title, $longitude, $latitude, $created_by_user_id = null)
    {
        if (is_null($created_by_user_id) == true) {
            $created_by_user_id = User::getLoggedUser()->getId();
        }

        $db = \Walleye\Database::getInstance();
        $insert_event_stmt = $db->prepare('INSERT INTO Events (title, longitude, latitude, created_by_user_id) VALUES (?, ?, ?, ?)');
        $insert_event_stmt->bind_param('sssi', $title, $longitude, $latitude, $created_by_user_id);
        if ($insert_event_stmt->execute() == true) {
            $event_id = $db->insert_id;
            return Event::withId($event_id);
        }
        return null;
    }

    /**
     * Commits any changes done to the public properties to the db
     *
     * @return bool
     */
    public function commit()
    {
        // title, desc
        $db = \Walleye\Database::getInstance();
        $update_event_stmt = $db->prepare('UPDATE Events SET title = ?, desc = ? WHERE id = ?');
        $update_event_stmt->bind_param('ssi', $this->title, $this->desc, $this->getId());
        return $update_event_stmt->execute();
    }

    /**
     * @return int
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * @return string
     */
    public function getTitle()
    {
        return $this->title;
    }

    /**
     * @return string
     */
    public function getDesc()
    {
        return $this->desc;
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
     * @return bool
     */
    public function isPublic()
    {
        if ($this->is_private != Event::IS_PRIVATE) return true;
        return false;
    }

    /**
     * @return bool
     */
    public function isPrivate()
    {
        if ($this->is_private == Event::IS_PRIVATE) return true;
        return false;
    }

    /**
     * @return User
     */
    public function getCreatedByUser()
    {
        return User::withId($this->created_by_user_id);
    }

    /**
     * Returns an array full of User objects. These user objects are all the users
     * associated with the Event.
     *
     * @param array $user_ids
     * @return array
     */
    public function getUsersAndGroups($user_ids = null)
    {
        $users = array();
        foreach ((is_null($user_ids) == true) ? $user_ids
                : $this->user_ids_and_groups as $user_id) {
            $users[] = User::withId($user_id);
        }
        return $users;
    }

    /**
     * Returns an array full of User objects. These user objects are all the users
     * who are going to the Event.
     *
     * @return array
     */
    public function getAttendingUsers()
    {
        return $this->getUsersAndGroups($this->attending_user_ids);
    }

    /**
     * Returns an array full of User objects. These user objects are all the users
     * who are not going to the Event.
     *
     * @return array
     */
    public function getDeclinedUsers()
    {
        return $this->getUsersAndGroups($this->declined_user_ids);
    }

    /**
     * Returns an array full of User objects. These user objects are all the users
     * who may be going to the Event.
     *
     * @return array
     */
    public function getMaybeUsers()
    {
        return $this->getUsersAndGroups($this->maybe_user_ids);
    }

    /**
     * Returns an array full of User objects. These user objects are all the users
     * who have not responded to the Event.
     *
     * @return array
     */
    public function getAwaitingResponseUsers()
    {
        return $this->getUsersAndGroups($this->awaiting_response_user_ids);
    }

    /**
     * @return string
     */
    public function getLongitude()
    {
        return $this->longitude;
    }

    /**
     * @return string
     */
    public function getLatitude()
    {
        return $this->latitude;
    }

    /**
     * Returns an array full of Item objects. The Item object is an item that is
     * marked as needed at this Event by the creator.
     *
     * @return array
     */
    public function getItems()
    {
        $items = array();
        foreach ($this->item_ids as $item_id) {
            $items[] = Item::withId($item_id);
        }
        return $items;
    }

    /**
     * Marks the Event as busted. if False is passed, then the Event is not busted
     *
     * @param bool $is_busted
     * @return bool
     */
    public function setBusted($is_busted = true)
    {
        if (($is_busted == true && $this->isBusted() == true) || ($is_busted == false && $this->isBusted() == false)) {
            return true;
        }

        $db = \Walleye\Database::getInstance();
        $update_is_busted_stmt = $db->prepare('UPDATE Events SET is_busted = ? WHERE id = ?');
        $update_is_busted_stmt->bind_param('ii', $is_busted, $this->getId());
        if ($update_is_busted_stmt->execute() == true) {
            $this->is_busted = $is_busted;
            return true;
        }
        return false;
    }

    /**
     * Marks the Event as over. if False is passed, then the Event is back on!
     *
     * @param bool $is_over
     * @return bool
     */
    public function setOver($is_over = true)
    {
        if (($is_over == true && $this->isOver() == true) || ($is_over == false && $this->isOver() == false)) {
            return true;
        }

        $db = \Walleye\Database::getInstance();
        $update_is_over_stmt = $db->prepare('UPDATE Events SET is_over = ? WHERE id = ?');
        $update_is_over_stmt->bind_param('ii', $is_over, $this->getId());
        if ($update_is_over_stmt->execute() == true) {
            $this->is_over = $is_over;
            return true;
        }
        return false;
    }

    /**
     * @return bool
     */
    public function isBusted()
    {
        if ($this->is_busted == Event::BUSTED) return true;
        return false;
    }

    public function isOver()
    {
        if ($this->is_over == Event::OVER) return true;
        return false;
    }

    /**
     * @return string
     */
    public function getDateCreated()
    {
        return $this->date_created;
    }

    /**
     * Add a user to the event. Will by default go to the 'awaiting response' group.
     * If the user is already a part of the Event, true will be returned.
     *
     * @param int $user_id
     * @return bool
     */
    public function addUser($user_id)
    {
        if ($this->hasUser($user_id)) {
            return true;
        }
        $db = \Walleye\Database::getInstance();
        $insert_user_stmt = $db->prepare('INSERT INTO EventUsers (event_id, user_id) VALUES (?, ?)');
        $insert_user_stmt->bind_param('ii', $this->getId(), $user_id);
        if ($insert_user_stmt->execute() == true) {
            $this->user_ids_and_groups[] = array(
                'user_id' => $user_id,
                'user_group' => Event::AWAITING_CONFIRMATION
            );
            $this->awaiting_response_user_ids[] = $user_id;
            return true;
        }
        return false;
    }

    /**
     * Checks if the Event is associated with the passed user id
     *
     * @param int $user_id
     * @return bool
     */
    public function hasUser($user_id)
    {
        foreach ($this->user_ids_and_groups as $user_id_and_group) {
            if ($user_id_and_group['user_id'] == $user_id) return true;
        }
        return false;
    }

    /**
     * Moves the user with the passed id from whatever group it is in to
     * the 'awaiting response' group
     *
     * @param User $user_id
     * @param int $group
     * @return bool
     */
    private function updateUserGroup($user_id, $group)
    {
        // if the current group is what the passed group is.. then leave it alone
        foreach ($this->getUsersAndGroups() as $user_id_and_group) {
            if ($user_id_and_group['user_id'] == $user_id) {
                if ($user_id_and_group['user_group'] == $group) {
                    return true;
                }
            }
        }

        // update the db
        $db = \Walleye\Database::getInstance();
        $update_event_user_group = $db->prepare('UPDATE EventUsers SET user_group = ? WHERE user_id = ? AND event_id = ?');
        $update_event_user_group->bind_param('iii', $group, $user_id, $this->getId());
        if ($update_event_user_group->execute() == true) {
            // remove from whatever group the user is in
            $all_users_by_group = array(
                $this->getAttendingUsers(),
                $this->getMaybeUsers(),
                $this->getDeclinedUsers(),
                $this->getAwaitingResponseUsers()
            );

            $current_group = 0;
            foreach ($all_users_by_group as $users_by_group) {
                $current_key = 0;
                foreach ($users_by_group as $user_in_group) {
                    if ($user_in_group == $user_id) {
                        $group = $all_users_by_group[$current_group];
                        unset($group[$current_key]);
                        break;
                    }
                }
            }

            // and put in the right group
            foreach ($this->user_ids_and_groups as $user_id_and_group) {
                if ($user_id_and_group == $user_id) {
                    $user_id_and_group['user_group'] = $group;
                }
            }

            switch ($group) {
                case Event::ATTENDING:
                    $this->attending_user_ids[] = $user_id;
                    break;
                case Event::AWAITING_CONFIRMATION:
                    $this->awaiting_response_user_ids[] = $user_id;
                    break;
                case Event::MAYBE:
                    $this->maybe_user_ids[] = $user_id;
                    break;
                case Event::DECLINED:
                    $this->declined_user_ids[] = $user_id;
                    break;
                default:
                    break;
            }

            return true;
        }
        return false;
    }

    public function updateUserToAwaitingResponse($user_id)
    {
        return $this->updateUserGroup($user_id, Event::AWAITING_CONFIRMATION);
    }

    /**
     * Moves the user with the passed id from whatever group it is in to
     * the 'accepted' group
     *
     * @param User $user_id
     * @return bool
     */
    public function updateUserToAttending($user_id)
    {
        return $this->updateUserGroup($user_id, Event::ATTENDING);
    }

    /**
     * Moves the user with the passed id from whatever group it is in to
     * the 'maybe' group
     *
     * @param User $user_id
     * @return bool
     */
    public function updateUserToMaybe($user_id)
    {
        return $this->updateUserGroup($user_id, Event::MAYBE);
    }

    /**
     * Moves the user with the passed id from whatever group it is in to
     * the 'declined' group
     *
     * @param User $user_id
     * @return bool
     */
    public function updateUserToDeclined($user_id)
    {
        return $this->updateUserGroup($user_id, Event::DECLINED);
    }

    /**
     * Removes a user from the event.
     *
     * @param int $user_id
     * @return bool
     */
    public function removeUser($user_id)
    {
        $db = \Walleye\Database::getInstance();
        $remove_user_stmt = $db->prepare('DELETE FROM Users WHERE id = ?');
        $remove_user_stmt->bind_param('i', $user_id);
        return $remove_user_stmt->execute();
    }

    public static function getAllEvents()
    {
        $db = \Walleye\Database::getInstance();
        $get_events_stmt = $db->prepare('SELECT id FROM Events');
        $get_events_stmt->execute();

        $events = array();
        $events_result = $db->getResult($get_events_stmt);
        foreach ($events_result as $event_row) {
            $events[] = Event::withId($event_row->id);
        }
        return $events;
    }
}

/* End of file */
