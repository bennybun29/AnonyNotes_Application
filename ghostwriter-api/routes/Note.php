<?php
require '../controllers/NoteController.php';

$noteController = new NoteController();
$data = json_decode(file_get_contents("php://input"), true);

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    if (isset($data['action']) && $data['action'] === 'createNote') {
        $noteController->createNote($data);
    }
} elseif ($_SERVER['REQUEST_METHOD'] === 'GET') {
    if (isset($_GET['note_id'])) {
        $noteController->getNoteById($_GET['note_id']);
    } else {
        $noteController->getNotes();
    }
} elseif ($_SERVER['REQUEST_METHOD'] === 'PUT') {
    $noteController->updateNote($data);
} elseif ($_SERVER['REQUEST_METHOD'] === 'DELETE') {
    $noteController->deleteNote($data);
}
?>
