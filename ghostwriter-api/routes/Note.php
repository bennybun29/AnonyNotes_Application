<?php
require '../controllers/NoteController.php';

$noteController = new NoteController();
$data = json_decode(file_get_contents("php://input"), true);

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    if (isset($data['action']) && $data['action'] === 'createNote') {
        $noteController->createNote($data);
    }
} elseif ($_SERVER['REQUEST_METHOD'] === 'GET') {
    $noteController->getNotes();
}
?>
