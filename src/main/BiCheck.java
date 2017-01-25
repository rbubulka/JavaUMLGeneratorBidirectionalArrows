package main;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import parsers.ClassParser.ClassParser;
public class BiCheck extends ClassParser {

	public BidirectionalArrowParser(ClassParser other) {
		super(other);
	}

	@Override
	public String parse(List nodes, Set<String> relations) {
		boolean red = false;
		for(org.objectweb.asm.tree.ClassNode cn: (List<org.objectweb.asm.tree.ClassNode>) nodes){
			List<Object> r1 = Arrays.asList(relations.toArray());
			Set<String> toRemove = new HashSet<String>();
			Set<String> toAdd = new HashSet<String>();
			for (int i = 0; i < r1.size(); i++) {
				String relation1 = (String) r1.get(i);
				String[] rel1 = relation1.split(" ");
				if(!rel1[0].contains(cn.name)){continue;}
				for (int j = i; j < r1.size(); j++) {
					String relation2 = (String) r1.get(j);
					if (relation1 != relation2) {
						
						String[] rel2 = relation2.split(" ");

						if (rel1.length >= 5 && rel2.length >= 5
								&& ((rel1[0]).equals(rel2[4]) || ("L" + rel1[0]).equals(rel2[4]))
								&& (rel1[4].equals("L" + rel2[0]) || rel1[4].equals(rel2[0])) && rel1[2].equals(rel2[2])) {
							toAdd.add(rel1[0] + " " + rel1[1] + "..." + rel1[3] + " " + "both" + rel1[2].trim() + " "
									+ rel2[1] + "..." + rel2[3] + " " + rel1[4] + " ,color=\"red\"");
							toRemove.add(relation1);
							red = true;
//							toRemove.add(relation2);
						}
					}
				}
			}
			relations.addAll(toAdd);
			relations.removeAll(toRemove);
		}
		return red?"color=red":"" ;
	}

}
