<?php

namespace App\Http\Controllers;

use App\Models\Notes;
use Illuminate\Http\Request;

class NotesController extends Controller
{
    
    public function index()
{
    // Retrieve all notes with only the required fields
    $notes = Notes::select('user_name', 'content', 'created_at')->get();

    // Return the notes as a JSON response
    return response()->json($notes);
}

    public function store(Request $request)
    {
        // Validate and create a new note
        $request->validate([
            'user_name' => 'required|string', 
            'content' => 'required|string',
            'anonymous' => 'required|boolean',
        ]);
    
        // Ensure either username or email is provided
        if (empty($request->user_name) && empty($request->user_email)) {
            return response()->json(['message' => "Either user_name or user_email is required."], 400);
        }
    
        // Create a note with either username or email
        $noteData = $request->only(['user_name', 'user_email', 'content', 'anonymous']);
        $note = Notes::create($noteData);
    
        return response()->json(['message' => "Note created successfully", 'note' => $note], 201);
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
