<?php
require_once '../config/Database.php';

class NoteController {

    private $db;

    public function __construct() {
        $this->db = (new Database())->connect();
    }

    // Create a note
    public function createNote($data) {
        $query = "INSERT INTO notes (user_name, content, anonymous, created_at) VALUES (:user_name, :content, :anonymous, NOW())";
        $stmt = $this->db->prepare($query);
        $stmt->bindParam(':user_name', $data['user_name']);
        $stmt->bindParam(':content', $data['content']);
        $stmt->bindParam(':anonymous', $data['anonymous']);
        $stmt->execute();
        echo json_encode(['message' => 'Note created successfully']);
    }

    // Retrieve all notes
    public function getNotes() {
        $query = "SELECT * FROM notes";
        $stmt = $this->db->prepare($query);
        $stmt->execute();
        $notes = $stmt->fetchAll(PDO::FETCH_ASSOC);
        echo json_encode($notes);
    }
}
?>
