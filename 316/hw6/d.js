db.committees.aggregate([
    {$addFields: { 
        numCommitteeMembers: {$size: "$members"},
    }},
    {$match : {"subcommittees": {$exists: true}}},
    {$addFields: { 
        numSubcommittees: {$size: "$subcommittees"} 
    }},
    {$project: {"_id":1,"displayname":1,"type":1, "numCommitteeMembers":1,"numSubcommittees":1, "members":1}},
    {$match: {
        "type":"house", 
        numCommitteeMembers: {$gte: 15}, 
        numSubcommittees: {$gte: 2},
    }},
    {
        $lookup:
        {
            from: "people",
            localField: "members.id",
            foreignField: "_id",
            as: "people"
        }
    },
    {$unwind: "$people"},
    {$replaceRoot: {"newRoot": "$people"}},
    {$project: {"_id":0, "name":1}},

   ]).toArray()