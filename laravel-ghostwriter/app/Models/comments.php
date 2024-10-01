<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Comments extends Model
{
    use HasFactory;

    // Specify the table associated with this model
    protected $table = 'comments';
    protected $primaryKey = 'comment_id';// Set the primary key


    // Define fillable fields for mass assignment
    protected $fillable = [
        'comment_id',
        'note_id',
        'user_name',
        'content',
        'anonymous',
        'created_at',
    ];

    //Relationships with other tables

    // Define the relationship with the `Users` model (Each comment belongs to a user)
    public function user()
    {
        return $this->belongsTo(Users::class, 'user_name', 'user_name');
    }

    // Define the relationship with the `Notes` model (Each comment belongs to a note)
    public function note()
    {
        return $this->belongsTo(Notes::class, 'note_id');
    }

    // Define the relationship with the `Hearts` model (A comment can have many hearts/likes)
    public function hearts()
    {
        return $this->hasMany(Hearts::class, 'comment_id');
    }
}
