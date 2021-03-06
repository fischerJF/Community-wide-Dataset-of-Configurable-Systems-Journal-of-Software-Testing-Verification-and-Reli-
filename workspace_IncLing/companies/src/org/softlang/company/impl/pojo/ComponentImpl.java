package org.softlang.company.impl.pojo;








import org.softlang.company.Component;
import org.softlang.visitor.ReturningVisitor;
import org.softlang.visitor.VoidVisitor;

public abstract class ComponentImpl implements Component {
	private String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public abstract void accept(VoidVisitor v);

	public abstract <R> R accept(ReturningVisitor<R> v);
}
