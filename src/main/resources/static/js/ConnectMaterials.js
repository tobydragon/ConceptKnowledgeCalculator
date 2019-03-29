const recordsFile = "/api/connectMaterials/Cs1Example";
const conceptsFile = "/api/conceptList/Cs1Example";

const records = readJson(recordsFile);
const concepts = readJson(conceptsFile);

const recordsList = document.getElementById("recordsList");
const tagsList = document.getElementById("tagsList");
var listItem;
var tagItem;
var content
var tags;
var color = '%c';

console.log(color.concat(JSON.stringify(concepts)), 'color: blue; font-weight: bold;');

for (var i = 0; i < records.length; i++) {
    listItem = document.createElement("li");
    tagItem = document.createElement("li");

    content = document.createTextNode(records[i].content);
    tags = document.createTextNode(Object.keys(records[i].tagsMap));

    tagItem.appendChild(tags);
    listItem.appendChild(content);

    tagsList.appendChild(tagItem);
    recordsList.appendChild(listItem);
}

