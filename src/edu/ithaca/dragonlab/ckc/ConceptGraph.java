package edu.ithaca.dragonlab.ckc;

import edu.ithaca.dragonlab.ckc.learningobject.LearningObject;
import edu.ithaca.dragonlab.ckc.learningobject.LearningObjectResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;


public class ConceptGraph {
    private static final Logger logger = LogManager.getLogger(ConceptGraph.class);
	public static final Integer DIVISION_FACTOR = 2;

	List<ConceptNode> roots;

	//This information will be duplicated, links will be found in nodes, but also here.
	Map<String, LearningObject> learningObjectMap;
	Map<String, ConceptNode> nodeMap;
	
	//TODO: This seems to use and change the same nodes. Calling this constructor twice would break things...
	public ConceptGraph(NodesAndIDLinks structureDef){
        learningObjectMap = new HashMap<>();
        nodeMap = new HashMap<>();
		List<ConceptNode> nodes =structureDef.getNodes();
		List<IDLink> links = structureDef.getLinks();

		this.roots = findRoots(nodes, links);
        this.nodeMap = addChildren(nodes, links);
	}

	public ConceptGraph(ConceptGraph graph){
		NodesAndIDLinks lists = graph.buildNodesAndLinks();
        List<ConceptNode> nodes = new ArrayList<>();
		List<ConceptNode> nodesIn = lists.getNodes();
		for(ConceptNode node: nodesIn){
			ConceptNode tempNode = new ConceptNode(node.getID());
			nodes.add(tempNode);
		}

		List<IDLink> links = new ArrayList<>();
		List<IDLink> linksIn = lists.getLinks();
		for(IDLink link: linksIn){
			IDLink tempLink = new IDLink(link.getParent(),link.getChild());
			links.add(tempLink);
		}
		
		this.roots = findRoots(nodes, links);
		this.nodeMap = addChildren(nodes, links);
	}

	//TODO: When book code is integrated
//	public ConceptGraph(Book b, NodesAndIDLinks lists){
//		List<ConceptNode> nodes = lists.getNodes();
//		List<IDLink> links = lists.getLinks();
//
//		List<IDLink> newLinks = b.buildTagLinks();
//		for(IDLink link : newLinks){
//			links.add(link);
//		}
//
//		this.roots = findRoots(nodes, links);
//
//		addChildren(nodes, links);
//	}

	/*
	 *Takes in a book, starts at the root, then goes through each level (chapters, sub chapters, questions) and creates
	 *a node for each concept, and adds it as a child.
	 */
	//TODO: fix this once the book is brought in
//	public ConceptGraph(Book b){
//		ConceptNode root = new ConceptNode(b);
//
//		//get the list of chapters of the book
//		//create a new node for each chapter
//		List<Chapter> chapters = b.getChapters();
//		for (Chapter chap : chapters){
//			ConceptNode chapNode = new ConceptNode(chap);
//			root.addChild(chapNode);
//
//			//get the list of sub chapters for each chapter
//			//create a new node for each sub chapter
//			List<SubChapter> subChaps = chap.getSubChapters();
//
//			for (SubChapter subChap : subChaps){
//				ConceptNode subChapNode = new ConceptNode(subChap);
//				chapNode.addChild(subChapNode);
//
//				//get the list of questions for each sub chapter
//				//create a new node for each question
//				List<Question> questions = subChap.getQuestions();
//				for(Question ques : questions){
//					ConceptNode quesNode = new ConceptNode(ques);
//					subChapNode.addChild(quesNode);
//				}
//			}
//		}
//		this.roots = new ArrayList<ConceptNode>();
//		this.roots.add(root);
//	}
	
	public ConceptGraph(List<ConceptNode> rootsIn){
        learningObjectMap = new HashMap<>();
        this.roots = rootsIn;
	}



    public void addLearningObjects(NodesAndIDLinks learningObjectDef){
        for (ConceptNode node : learningObjectDef.getNodes()){
            learningObjectMap.put(node.getID(), new LearningObject(node.getID()));
        }
        for (IDLink link : learningObjectDef.getLinks()){
            ConceptNode node = nodeMap.get(link.getParent());
            if (node != null){
                node.addLearningObject(learningObjectMap.get(link.getChild()));
            }
            else {
                logger.warn("Adding a Learning Object:" + link.getChild() + " has no matching node in the graph:" + link.getParent());
            }
        }
    }

    public void addSummariesToGraph(List<LearningObjectResponse> summaries){
        for (LearningObjectResponse response : summaries) {
            LearningObject learningObject = learningObjectMap.get(response.getLearningObjectId());
            if (learningObject != null) {
                learningObject.addResponse(response);
            } else {
                logger.warn("No learning object:" + response.getLearningObjectId() + " for response: " + response.toString());
                //TODO: maybe make a new list of unconnected learning objects???
            }
        }
	}	

	private Map<String, ConceptNode> addChildren(List<ConceptNode> nodes, List<IDLink> links){
		HashMap<String, ConceptNode> fullNodesMap = new HashMap<>();
		for( ConceptNode currNode : nodes){
			fullNodesMap.put(currNode.getID(), currNode);
		}
		
		for( IDLink currLink : links){
			ConceptNode currParent = fullNodesMap.get(currLink.getParent());
			if(currParent == null){
				logger.warn("In ConceptGraph.addChildren(): " + currLink.getParent()+" node not found in nodes list for link " + currLink);
			}
			else{
				ConceptNode currChild = fullNodesMap.get(currLink.getChild());
				if(currChild == null){
					logger.warn("In ConceptGraph.addChildren(): " + currLink.getChild()+" node not found in nodes list for link " + currLink);
				}
				else{
					currParent.addChild(currChild);
				}
			}
			
		}
		return fullNodesMap;
	}
	
 	private List<ConceptNode> findRoots(List<ConceptNode> nodes, List<IDLink> links) {
		List<ConceptNode> runningTotal = new ArrayList<>();
		for (ConceptNode node : nodes) {
			runningTotal.add(node);
		}
		for (IDLink link : links) {
			for(ConceptNode node : nodes){
	 			if(node.getID().equals(link.getChild())){
	 				runningTotal.remove(node);
	 			}
	 		}
		}
		return runningTotal;
	}
	
	public String toString(){
		NodesAndIDLinks thisGraph = this.buildNodesAndLinks();
		return "Nodes:\n"+thisGraph.getNodes()+"\nLinks:\n"+thisGraph.getLinks();
		
		//return roots.toString();
		
	}
		
	public NodesAndIDLinks buildNodesAndLinks() {
		List<ConceptNode> tempNodes = new ArrayList<ConceptNode>();
		List<IDLink> tempLinks = new ArrayList<IDLink>();
		for(ConceptNode currRoot : this.roots){
			currRoot.addToNodesAndLinksLists(tempNodes,tempLinks);
		}
		NodesAndIDLinks outputLists = new NodesAndIDLinks(tempNodes, tempLinks);
		return outputLists;
	}
		

	public void calcActualComp(){
		for(ConceptNode root : this.roots){
			root.calcActualComp();
		}
	}

	public void calcPredictedScores() {
		for(ConceptNode root : this.roots){
			calcPredictedScores(root);
		}
		
	}
	
	//TODO: currentRoot.getActualComp used to be this.root.getActualComp, analyze how this changed things
	private void calcPredictedScores(ConceptNode currentRoot) {
		calcPredictedScores(currentRoot, currentRoot.getActualComp(), currentRoot);
	}
	
	// pre order traversal
	private static void calcPredictedScores(ConceptNode current, double passedDown, ConceptNode currentRoot) {
		
		// simple check for if we"re dealing with the root, which has its own rule
		if (current == currentRoot) {
			current.setPredictedComp(current.getActualComp());
		} else {
			current.setNumParents(current.getNumParents() + 1);
			
			// Calculating the new predicted, take the the old predicted with the weight it has based off of the number of parents
			// calculate the new pred from the new information passed down and the adding it to old pred
			double oldPred = current.getPredictedComp() * (1.0 - (1.0/current.getNumParents()));
			double newPred = (passedDown * (1.0/current.getNumParents())) + oldPred;
			
			current.setPredictedComp(newPred);
		}
		
		for (ConceptNode child : current.getChildren()) {
			if (current.getActualComp() == 0) {
				
				calcPredictedScores(child, current.getPredictedComp()/ DIVISION_FACTOR, currentRoot);
			} else {
				calcPredictedScores(child, current.getActualComp(), currentRoot);
			}
		}
		
	}
	
	public ConceptGraph graphToTree(){
		List<ConceptNode> newRoots = new ArrayList<ConceptNode>();
		HashMap<String, List<String>> initMultCopies = new HashMap<String, List<String>>();
		for(ConceptNode root : this.roots){
			newRoots.add(root.makeTree(initMultCopies));
		}
		
		return new ConceptGraph(newRoots);
		
	}
	
	public ConceptNode findNodeById(String id){
		return nodeMap.get(id);
	}

    public Map<String, LearningObject> getLearningObjectMap() {
        return learningObjectMap;
    }
}
