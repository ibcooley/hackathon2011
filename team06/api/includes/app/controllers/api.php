<?php

namespace App\Controllers;

/**
 * The controller for a public or protected API
 */
class Api extends \Walleye\Controller
{

    /*
     * @see Walleye_controller
     * @param string $url
     * @param array $data
     */
    public function __construct($url, $data)
    {
        $this->url = $url;
        $this->data = $data;
        $this->path = $this->getUrlPath($url);
        $this->handlers = array(
            '/^(\/api\/user)$/' => 'usersHandler',
            '/^(\/api\/user\/\d+)$/' => 'userHandler',
            '/^(\/api\/event)$/' => 'eventsHandler',
            '/^(\/api\/event\/\d+)$/' => 'eventHandler',
            '/^(\/api\/event\/\d+\/comment)$/' => 'eventCommentsHandler',
            '/^(\/api\/event\/\d+\/comment\/\d+)$/' => 'eventCommentHandler',
            '/^(\/api\/event\/\d+\/item)$/' => 'eventItemsHandler',
            '/^(\/api\/event\/\d+\/item\/\d+)$/' => 'eventItemHandler',
            '/^(\/api\/event\/\d+\/busted)$/' => 'eventBustedHandler',
            '/^(\/api\/event\/\d+\/over)$/' => 'eventOverHandler',
            'default' => 'error_404'
        );
    }

    /**
     * Return all users
     */
    protected function usersHandler()
    {
        if ($this->isGet() == true) {
            // return all users
            $this->useJsonHeader();
            $users = \Simplevent\Models\User::getAllUsers();

            $return_data = array();
            foreach ($users as $user) {
                $return_data[] = array(
                    'id' => $user->getId(),
                    'first_name' => $user->firstName,
                    'last_name' => $user->lastName,
                    'email' => $user->getUsername(),
                    'display_name' => $user->getDisplayName(),
                    'date_created' => $user->getDateCreated()
                );
            }
            echo json_encode(array(
                                  'stat' => 'ok',
                                  'data' => $return_data
                             ));
        }
        else if ($this->isPost() == true) {
            // create user
        }
        else {
            $this->error_405();
        }
    }

    protected function userHandler()
    {
        if ($this->isGet() == true) {
            // return user

        }
        else if ($this->isDelete() == true) {
            // remove user
        }
        else if ($this->isPut() == true) {
            // update user
        }
        else {
            $this->error_405();
        }
    }

    /**
     * Make sure geolocation data is passed otherwise the top 10 Events (in order)
     * will be returned
     *
     */
    protected function eventsHandler()
    {
        if ($this->isGet() == true) {
            // return all events
            $this->useJsonHeader();

            $events = \Simplevent\Models\Event::getAllEvents();
            $return_data = array();
            foreach ($events as $event) {
                $return_data[] = array(
                    'id' => $event->getId(),
                    'title' => $event->title,
                    'desc' => $event->desc,
                    'created_by_user_id' => (is_null($event->getCreatedByUser()) == false)
                            ? $event->getCreatedByUser()->getId()
                            : \Simplevent\Models\Event::ID_OF_UNKNOWN_EVENT,
                    'is_private' => $event->isPrivate(),
                    'is_over' => $event->isOver(),
                    'is_busted' => $event->isBusted(),
                    'date_created' => $event->getDateCreated()
                );
            }
            echo json_encode(array(
                                  'stat' => 'ok',
                                  'data' => $return_data
                             )
            );
        }
        else if ($this->isPost()) {
            // create event
            $title = $this->data['title'];
            $longitude = $this->data['longitude'];
            $latitude = $this->data['latitude'];
            $created_by_user_id = $this->data['created_by_user_id'];

            if ($event = \Simplevent\Models\Event::create($title, $longitude, $latitude, $created_by_user_id)) {
                echo json_encode(array(
                                      'stat' => 'ok',
                                      'data' => array(
                                          array(
                                              'id' => $event->getId(),
                                              'date_created' => $event->getDateCreated()
                                          )
                                      )
                                 ));
            }
            else {
                echo json_encode(array(
                                      'stat' => 'error'
                                 ));
            }
        }
        else {
            $this->error_405();
        }
    }

    protected function eventHandler()
    {
        if ($this->isGet() == true) {
            // return event
            $event_id = $this->path[2];

            if ($event = \Simplevent\Models\Event::withId($event_id)) {

                $attending_users = $event->getAttendingUsers();
                $attending_user_ids = array();
                foreach ($attending_users as $user) {
                    $attending_user_ids[] = $user->getId();
                }

                $declined_users = $event->getDeclinedUsers();
                $declined_user_ids = array();
                foreach ($declined_users as $user) {
                    $declined_user_ids[] = $user->getId();
                }

                $maybe_users = $event->getMaybeUsers();
                $maybe_user_ids = array();
                foreach ($maybe_users as $user) {
                    $maybe_user_ids[] = $user->getId();
                }

                $awaiting_response_users = $event->getAwaitingResponseUsers();
                $awaiting_response_user_ids = array();
                foreach ($awaiting_response_users as $user) {
                    $awaiting_response_user_ids[] = $user->getId();
                }

                $items = $event->getItems();
                $item_ids = array();
                foreach ($items as $item) {
                    $item_ids[] = $item->getId();
                }

                $comments = $event->getComments();
                $comment_ids = array();
                foreach ($comments as $comment) {
                    $comment_ids[] = $comment->getId();
                }

                echo json_encode(array(
                                      'stat' => 'ok',
                                      'data' => array(
                                          'id' => $event->getId(),
                                          'title' => $event->getTitle(),
                                          'desc' => $event->getDesc(),
                                          'longitude' => $event->getLongitude(),
                                          'latitude' => $event->getLatitude(),
                                          'date_created' => $event->getDateCreated(),
                                          'items' => $items,
                                          'comments' => $comments,
                                          'created_by_user_id' => (is_null($event->getCreatedByUser()) == false)
                                                  ? $event->getCreatedByUser()->getId()
                                                  : \Simplevent\Models\User::ID_OF_UNKNOWN_USER,
                                          'is_private' => $event->isPrivate(),
                                          'attending_user_ids' => $attending_user_ids,
                                          'declined_user_ids' => $declined_user_ids,
                                          'maybe_user_ids' => $maybe_user_ids,
                                          'awaiting_response_user_ids' => $awaiting_response_user_ids
                                      )
                                 ));
            }
            else {
                echo json_encode(array(
                                      'stat' => 'error'
                                 ));
            }
        }
        else if ($this->isDelete() == true) {
            // remove event
        }
        else if ($this->isPut() == true) {
            // update event
        }
        else {
            $this->error_405();
        }
    }

    protected function eventCommentsHandler()
    {
        if ($this->isGet() == true) {
            // return all comments
            $this->useJsonHeader();
            $event_id = $this->path[2];
            $return_data = array();

            if ($event = \Simplevent\Models\Event::withId($event_id)) {
                $comments = $event->getComments();
                foreach ($comments as $comment) {
                    $return_data[] = array(
                        'id' => $comment->getId(),
                        'comment' => $comment->getComment(),
                        'created_by_user_id' => (is_null($comment->getCreatedByUser()) != null)
                                ? $comment->getCreatedByUser()->getId() : 0,
                        'date_created' => $comment->getDateCreated()
                    );
                }
            }

            echo json_encode(array(
                                  'stat' => 'ok',
                                  'data' => $return_data
                             ));

        }
        else if ($this->isPost() == true) {
            // create comment
            $this->useJsonHeader();
            $comment = $this->data['comment'];
            $created_by_user_id = $this->data['created_by_user_id'];
            $event_id = $this->data['event_id'];

            if ($comment = \Simplevent\Models\Comment::create($comment, $event_id, $created_by_user_id)) {
                echo json_encode(array(
                                      'stat' => 'ok',
                                      'data' => array(
                                          array(
                                              'id' => $comment->getId(),
                                              'comment' => $comment->getComment(),
                                              'date_created' => $comment->getDateCreated(),
                                              'event_id' => (is_null($comment->getEvent()) == false)
                                                      ? $comment->getEvent()->getId()
                                                      : \Simplevent\Models\Comment::ID_OF_UNKNOWN_COMMENT
                                          )
                                      )
                                 ));
            }
        }
        else {
            $this->error_405();
        }
    }

    protected function eventCommentHandler()
    {
        if ($this->isGet() == true) {
            // return comment
        }
        else if ($this->isDelete() == true) {
            // remove comment
            $this->useJsonHeader();
            $comment_id = $this->path[4];
            $comment = \Simplevent\Models\Comment::withId($comment_id);
            if ($comment->remove() == true) {
                echo json_encode(array(
                                      'stat' => 'ok'
                                 ));
            }
            else {
                echo json_encode(array(
                                      'stat' => 'error'
                                 ));
            }
        }
        else if ($this->isPut() == true) {
            // update comment
        }
        else {
            $this->error_405();
        }
    }

    protected function eventItemsHandler()
    {
        if ($this->isGet() == true) {
            // return all items
            $event_id = $this->path[2];
            $return_data = array();

            if ($event = \Simplevent\Models\Event::withId($event_id)) {
                $items = $event->getItems();
                foreach ($items as $item) {
                    $return_data[] = array(
                        'id' => $item->getId(),
                        'title' => $item->getTitle(),
                        'desc' => $item->getDesc(),
                        'event_id' => (is_null($item->getEvent()) == false)
                                ? $item->getEvent()->getId()
                                : \Simplevent\Models\Item::ID_OF_UNKNOWN_ITEM,
                        'created_by_user_id' => (is_null($item->getCreatedByUser()) == false)
                                ? $item->getCreatedByUser()->getId()
                                : \Simplevent\Models\User::ID_OF_UNKNOWN_USER,
                        'claimed_by_user_id' => (is_null($item->getClaimedByUser()) == false)
                                ? $item->getClaimedByUser()->getId()
                                : \Simplevent\Models\User::ID_OF_UNKNOWN_USER,
                        'date_created' => $item->getDateCreated()
                    );
                }
            }

            echo json_encode(array(
                                  'stat' => 'ok',
                                  'data' => $return_data
                             ));

        }
        else if ($this->isPost() == true) {
            // create item
        }
        else {
            $this->error_405();
        }
    }

    protected function eventItemHandler()
    {
        if ($this->isGet() == true) {
            // return item
        }
        else if ($this->isDelete() == true) {
            // remove item
        }
        else if ($this->isPut() == true) {
            // update event
        }
        else {
            $this->error_405();
        }
    }

    /**
     * Set the passed event as busted
     */
    protected function eventBustedHandler()
    {
        if ($this->isPut() == true) {
            // mark event as busted or not
            $this->useJsonHeader();
            $is_busted = $this->data['is_busted'];
            $event_id = $this->path[2];

            $event = \Simplevent\Models\Event::withId($event_id);
            if ($event->setBusted($is_busted) == true) {
                echo json_encode(array(
                                      'stat' => 'ok'
                                 ));
            }
            else {
                echo json_encode(array(
                                      'stat' => 'error'
                                 ));
            }
        }
        else {
            $this->error_405();
        }
    }

    /**
     * Set the passed event as over
     */
    protected function eventOverHandler()
    {
        if ($this->isPut() == true) {
            // mark event as over or not
            $this->useJsonHeader();
            $is_over = $this->data['is_over'];
            $event_id = $this->path[2];

            $event = \Simplevent\Models\Event::withId($event_id);
            if ($event->setOver($is_over) == true) {
                echo json_encode(array(
                                      'stat' => 'ok'
                                 ));
            }
            else {
                echo json_encode(array(
                                      'stat' => 'error'
                                 ));
            }
        }
        else {
            $this->error_405();
        }
    }
}

/* End of file */
