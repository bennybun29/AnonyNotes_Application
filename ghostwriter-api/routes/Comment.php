<?php
require '../controllers/CommentController.php';

$commentController = new CommentController();
$data = json_decode(file_get_contents("php://input"), true);

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    if (isset($data['action']) && $data['action'] === 'createComment') {
        $commentController->createComment($data);
    }
} elseif ($_SERVER['REQUEST_METHOD'] === 'GET') {
    if (isset($_GET['note_id'])) {
        $commentController->getComments($_GET['note_id']);
    }
}
?>
