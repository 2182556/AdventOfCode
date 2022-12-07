class DirTree:
    def __init__(self, data):
        self.data=data
        self.children = [];
        self.parent = None;
        
    def __iter__(self):
        for child in self.children:
            for item in child: yield item            
        yield self.data[1]
            
    def getTotalSize(self):
        self.data[1] += sum(child.getTotalSize() for child in self.children);
        return self.data[1]
    
    def findRoot(self):
        if (self.parent is None): return self;
        else: return self.parent.findRoot();
        
with open('input.txt') as f:
    currentNode = DirTree(['/', 0])
    for l in f:
        if (l[0] is not '$'):            
            if (l[0:3] == 'dir'):
                newDir = DirTree([l[4:].strip(), 0])
                newDir.parent = currentNode
                currentNode.children.append(newDir)
            else:
                currentNode.data[1] += int(l.split(' ')[0])
        else:
            if (l.split(' ')[1] == 'cd'):
                if (l.split(' ')[2].strip() == '..'): currentNode = currentNode.parent                    
                else: 
                    for (i, child) in enumerate(currentNode.children):
                        if (child.data[0] == l.split(' ')[2].strip()): currentNode = child                            

    currentNode = currentNode.findRoot();
    currentNode.getTotalSize();
    nodes = list(iter(currentNode));
    
    max = 100000;
    count = 0;
    
    amountOfSpaceNeeded = currentNode.data[1] - 40000000;
    closestMatch = currentNode.data[1];
    for node in nodes:
        if (node <= max): count += node
        if ((node - amountOfSpaceNeeded) >= 0 and node < closestMatch): closestMatch = node;
            
    print('Solution part one: ', count)
    print('Solution part two: ', closestMatch)        