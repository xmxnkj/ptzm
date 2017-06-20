
function convert(rows, rootText){
	return convertex(rows.rows, rootText);
}

function convertex(rows, rootText) {
    var nodes = [];

    // get the top level nodes
    for (var i = 0; i < rows.length; i++) {
        var row = rows[i];
        if (!exists(rows, row.parentId)) {
            nodes.push({
                id: row.id,
                text: row.name
            });
        }
    }

    var toDo = [];
    for (var i = 0; i < nodes.length; i++) {
        toDo.push(nodes[i]);
    }
    while (toDo.length) {
        var node = toDo.shift(); // the parent node
        // get the children nodes
        for (var i = 0; i < rows.length; i++) {
            var row = rows[i];
            if (row.parentId == node.id) {
                var child = { id: row.id, text: row.name };
                if (node.children) {
                    node.children.push(child);
                } else {
                    node.children = [child];
                }
                toDo.push(child);
            }
        }
    }

    var treeNodes = [];
    var rt = rootText != null ? rootText : "";
    var root = { id: "", text: rootText };
    root.children = nodes;
    treeNodes.push(root);
    return treeNodes;
}


function exists(rows, ParentId) {
    for (var i = 0; i < rows.length; i++) {
        if (rows[i].id == ParentId)
            return true;
    }
    return false;
}

function setTreeNode(treeId, nodeId) {
    var tree = $("#" + treeId).combotree("tree");
    var node = tree.tree("find", nodeId);
    if (node != null) {
        tree.tree('select', node.target);
        $('#' + treeId).combotree('setValue', node.id);
    }
}

function checkComboTreeNodeById(comboboxId, nodeId){
	var tree = $("#" + comboboxId).combotree("tree");
	checkTreeNode(tree, nodeId);
}

function setCheckComboTreeNode(comboboxId, nodeIds){
	var arr = nodeIds.split(";");
	var tree = $("#" + comboboxId).combotree("tree");
	for(var i=0;i<arr.length;i++){
		checkTreeNode(tree, arr[i]);
	}
}

function checkTreeNode(tree, nodeId){
	if(nodeId != null && nodeId != ""){
		var node = tree.tree("find", nodeId);
	    if (node != null) {
	        tree.tree('check', node.target);
	    }
	}
}
