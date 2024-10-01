<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Notes extends Model
{
    use HasFactory;

    //table associated with this model
    protected $table = 'notes';
    protected $primaryKey = 'note_id';  // Primary key for the table

    // Define fillable fields for mass assignment
    protected $fillable = [
        'note_id',
        'user_name',
        'content',
        'anonymous',
        'created_at',
    ];
    //Relationships with other tables
    
    // Define the relationship with the `Users` model (Each note belongs to a user)
    public function user()
    {
        return $this->belongsTo(Users::class, 'user_name', 'user_name');
    }

    // Define the relationship with the `Comments` model (A note can have many comments)
    public function comments()
    {
        return $this->hasMany(Comments::class, 'note_id');
    }

    // Define the relationship with the `Hearts` model (A note can have many hearts/likes)
    public function hearts()
    {
        return $this->hasMany(Hearts::class, 'note_id');
    }
}
