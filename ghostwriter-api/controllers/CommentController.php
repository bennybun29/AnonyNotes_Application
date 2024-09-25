<?php
require '../models/Comment.php';

class CommentController {
    private $comment;

    public function __construct() {
        $this->comment = new Comment((new Database())->getConnection());
    }

    // Create a comment
    public function createComment($data) {
        $this->comment->note_id = $data['note_id'];
        $this->comment->user_name = $data['user_name'];
        $this->comment->content = $data['content'];
        $this->comment->anonymous = $data['anonymous'];
        $this->comment->created_at = date('Y-m-d H:i:s');

        if ($this->comment->create()) {
            echo json_encode(['message' => 'Comment created successfully.']);
        } else {
            echo json_encode(['message' => 'Comment creation failed.']);
        }
    }

    // Get comments by note ID
    public function getComments($note_id) {
        $comments = $this->comment->readByNoteId($note_id);
        echo json_encode($comments);
    }

    // Update a comment
    public function updateComment($data) {
        $this->comment->comment_id = $data['comment_id'];
        $this->comment->content = $data['content'];
        $this->comment->anonymous = $data['anonymous'];

        $this->comment->update();
        echo json_encode(['message' => 'Comment updated successfully.']);
    }

    // Delete a comment
    public function deleteComment($data) {
        $this->comment->comment_id = $data['comment_id'];
        $this->comment->delete();
        echo json_encode(['message' => 'Comment deleted successfully.']);
    }
}
?>
