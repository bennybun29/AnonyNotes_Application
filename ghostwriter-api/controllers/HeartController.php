<?php
require_once '../config/Database.php';

class HeartController {

    private $db;

    public function __construct() {
        $this->db = (new Database())->getConnection();
    }

    // Add a heart
    public function addHeart($data) {
        $query = "INSERT INTO hearts (comment_id, note_id, user_name, created_at) VALUES (:comment_id, :note_id, :user_name, NOW())";
        $stmt = $this->db->prepare($query);
        $stmt->bindParam(':comment_id', $data['comment_id']);
        $stmt->bindParam(':note_id', $data['note_id']);
        $stmt->bindParam(':user_name', $data['user_name']);
        $stmt->execute();
        echo json_encode(['message' => 'Heart added successfully']);
    }

    // Get hearts for a note
    public function getHearts($note_id) {
        $query = "SELECT * FROM hearts WHERE note_id = :note_id";
        $stmt = $this->db->prepare($query);
        $stmt->bindParam(':note_id', $note_id);
        $stmt->execute();
        $hearts = $stmt->fetchAll(PDO::FETCH_ASSOC);
        echo json_encode($hearts);
    }
}
?>
