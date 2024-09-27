<?php
require '../models/Note.php';
require '../config/database.php';
class NoteController {
    private $note;

    public function __construct() {
        $this->note = new Note((new Database())->getConnection());
    }

    // Create a note
    public function createNote($data) {
        $this->note->user_name = $data['user_name'];
        $this->note->content = $data['content'];
        $this->note->anonymous = $data['anonymous'];
        $this->note->created_at = date('Y-m-d H:i:s');

        if ($this->note->create()) {
            echo json_encode(['message' => 'Note created successfully.']);
        } else {
            echo json_encode(['message' => 'Note creation failed.']);
        }
    }

    // Get all notes
    public function getNotes() {
        $notes = $this->note->read();
        echo json_encode($notes->fetchAll(PDO::FETCH_ASSOC));
    }

    // Get note by ID
    public function getNoteById($id) {
        $note = $this->note->read($id);
        echo json_encode($note);
    }

    // Update a note
    public function updateNote($data) {
        $this->note->note_id = $data['note_id'];
        $this->note->content = $data['content'];
        $this->note->anonymous = $data['anonymous'];

        $this->note->update();
        echo json_encode(['message' => 'Note updated successfully.']);
    }

    // Delete a note
    public function deleteNote($data) {
        $this->note->note_id = $data['note_id'];
        $this->note->delete();
        echo json_encode(['message' => 'Note deleted successfully.']);
    }
}
?>
