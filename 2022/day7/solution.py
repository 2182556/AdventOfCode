class DirNode:
    def __init__(self, data, parent=None):
        self.data=data
        self.children = []
        self.parent = parent
        
    def __iter__(self):
        for child in self.children:
            for item in child: yield item            
        yield self.data[1]
            
    def getTotalSize(self):
        self.data[1] += sum(child.getTotalSize() for child in self.children)
        return self.data[1]
    
    def findRoot(self):
        if (self.parent is None): return self
        else: return self.parent.findRoot()
        
with open('input.txt') as f:
    currentNode = DirNode(['/', 0])
    for l in f:
        if (l[0] is not '$'):            
            if (l[0:3] == 'dir'):
                newDir = DirNode([l[4:].strip(), 0], currentNode)
                currentNode.children.append(newDir)
            else:
                currentNode.data[1] += int(l.split(' ')[0])
        else:
            if (l.split(' ')[1] == 'cd'):
                if (l.split(' ')[2].strip() == '..'): currentNode = currentNode.parent                 
                else: 
                    for (i, child) in enumerate(currentNode.children):
                        if (child.data[0] == l.split(' ')[2].strip()): currentNode = child                            

    rootNode = currentNode.findRoot()
    rootNode.getTotalSize()
    nodes = list(iter(rootNode))
    
    max = 100000
    count = 0
    
    amountOfSpaceNeeded = rootNode.data[1] - 40000000
    closestMatch = rootNode.data[1]
    for node in nodes:
        if (node <= max): count += node
        if ((node - amountOfSpaceNeeded) >= 0 and node < closestMatch): closestMatch = node
            
    print('Solution part one: ', count)
    print('Solution part two: ', closestMatch)