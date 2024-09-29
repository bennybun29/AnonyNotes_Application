<?php

namespace App\Http\Controllers;

use App\Models\Notes;
use Illuminate\Http\Request;

class NotesController extends Controller
{
    public function index()
    {
        // Retrieve all notes
        return Notes::all();
    }

    public function store(Request $request)
    {
        // Validate and create a new note
        $request->validate([
            'user_name' => 'required|string',
            'content' => 'required|string',
            'anonymous' => 'required|boolean',
        ]);

        // Create a note
        $note = Notes::create($request->all());

        return response()->json($note, 201);
    }

    public function show($id)
    {
        // Find and show a specific note by ID
        $note = Notes::find($id);

        if (!$note) {
            return response()->json(['message' => 'Note not found'], 404);
        }

        return response()->json($note);
    }

    public function update(Request $request, $id)
    {
        // Update the specified note
        $note = Notes::find($id);

        if (!$note) {
            return response()->json(['message' => 'Note not found'], 404);
        }

        $note->update($request->all());

        return response()->json($note);
    }

    public function destroy($id)
    {
        // Delete a note
        $note = Notes::find($id);

        if (!$note) {
            return response()->json(['message' => 'Note not found'], 404);
        }

        $note->delete();

        return response()->json(['message' => 'Note deleted']);
    }
}
