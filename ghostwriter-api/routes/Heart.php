<?php
require '../controllers/HeartController.php';

$heartController = new HeartController();
$data = json_decode(file_get_contents("php://input"), true);

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    if (isset($data['action']) && $data['action'] === 'addHeart') {
        $heartController->addHeart($data);
    }
} elseif ($_SERVER['REQUEST_METHOD'] === 'GET') {
    if (isset($_GET['note_id'])) {
        $heartController->getHearts($_GET['note_id']);
    }
}
?>
