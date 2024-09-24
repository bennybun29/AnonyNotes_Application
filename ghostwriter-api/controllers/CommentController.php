<?php
require_once '../config/Database.php';

class CommentController {

    private $db;

    public function __construct() {
        $this->db = (new Database())->getConnection();
    }

    // Create a comment
    public function createComment($data) {
        $query = "INSERT INTO comment (note_id, user_name, content, anonymous, created_at) VALUES (:note_id, :user_name, :content, :anonymous, NOW())";
        $stmt = $this->db->prepare($query);
        $stmt->bindParam(':note_id', $data['note_id']);
        $stmt->bindParam(':user_name', $data['user_name']);
        $stmt->bindParam(':content', $data['content']);
        $stmt->bindParam(':anonymous', $data['anonymous']);
        $stmt->execute();
        echo json_encode(['message' => 'Comment created successfully']);
    }

    // Retrieve comments for a note
    public function getComments($note_id) {
        $query = "SELECT * FROM comment WHERE note_id = :note_id";
        $stmt = $this->db->prepare($query);
        $stmt->bindParam(':note_id', $note_id);
        $stmt->execute();
        $comments = $stmt->fetchAll(PDO::FETCH_ASSOC);
        echo json_encode($comments);
    }
}
?>
