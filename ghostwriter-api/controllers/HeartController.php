<?php
require '../models/Heart.php';
require '../config/database.php';
class HeartController {
    private $heart;

    public function __construct() {
        $this->heart = new Heart((new Database())->getConnection());
    }

    // Add heart (like)
    public function addHeart($data) {
        $this->heart->comment_id = $data['comment_id'] ?? null;
        $this->heart->note_id = $data['note_id'] ?? null;
        $this->heart->user_name = $data['user_name'];
        $this->heart->created_at = date('Y-m-d H:i:s');

        if ($this->heart->create()) {
            echo json_encode(['message' => 'Heart added successfully.']);
        } else {
            echo json_encode(['message' => 'Failed to add heart.']);
        }
    }

    // Get hearts for a note or comment
    public function getHearts($note_id) {
        $hearts = $this->heart->readByNoteOrComment($note_id, null);
        echo json_encode($hearts);
    }

    // Remove a heart
    public function removeHeart($data) {
        $this->heart->heart_id = $data['heart_id'];
        $this->heart->delete();
        echo json_encode(['message' => 'Heart removed successfully.']);
    }
}
?>
