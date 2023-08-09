db.people.aggregate([
    {$unwind: {path: "$roles"}},
    {$match: {'roles.current': 1, "name": /.*D.*/, "gender": "F"}},
    {$project: {_id: false, roles: true}}
]).toArray()