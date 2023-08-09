db.committees.aggregate([
    {$unwind: "$subcommittees"},
    {$addFields: {"subcommittees.committeeID": "$_id"}},
    {$replaceRoot: { "newRoot": "$subcommittees"}},
    {
        $lookup:
        {
            from: "people",
            localField: "members.id",
            foreignField: "_id",
            as: "people"
        }
    },
    {$addFields: { totalmembers: { $size: "$members"} } },
    {$project: {"_id":1, "code":1, "people":1, "totalmembers":1, "committeeID":1}},
    
    {$unwind: {"path": "$people", "preserveNullAndEmptyArrays": true}},

    {$match: {$or: [
        {"people.birthday": {$lt : ISODate("1980-01-01T00:00:00Z")}},
    ]}},
    
    {$group: { _id: {"code":"$code", "committeeID":"$committeeID"}, membercount: { $sum: 1} } },
    {$match : {$or: [
        {"membercount" : {$lte: 5}},
    ]}},
    
    {$group: {_id: null, cnt: { $sum: 1 } } },
    {$project: {_id:0, cnt:1}}
   ]).toArray()