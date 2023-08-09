db.committees.aggregate([
    {$match: {"type":"house"}},
    {$unwind: "$subcommittees"},
    {$project: {"subcommittees":1}},
    {$replaceRoot: { "newRoot": "$subcommittees"}},

    {$addFields: {membercount : {$size: "$members"}}},
    {$match: {"membercount":{$gte:20}}},
    {$project: {_id: false, "displayname": 1}},
    
]).toArray()
