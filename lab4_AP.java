package shagun_lab4;

import java.util.*;

public class lab4_AP
{
	
	public static void main(String args[])
	{
		World w= new World();
		w.input();
	}
}

class Compare implements Comparator<Animal>
{
	public int compare(Animal a1,Animal a2)
	{
		if(a1.get_timestamp()<a2.get_timestamp())
			return -1;
		else if(a1.get_timestamp()>a2.get_timestamp())
			return 1;
		else
		{
			if(a1.get_health()>a2.get_health())
				return -1;
			else if(a1.get_health()<a2.get_health())
				return 1;
			else
			{
				if(a1.getClass().getName().equals("Herbivore") && a2.getClass().getName().equals("Carnivore"))
					return -1;
				else if(a1.getClass().getName().equals("Carnivore") && a2.getClass().getName().equals("Herbivore"))
					return 1;
				else if(a1.getClass().getName().equals("Herbivore") && a2.getClass().getName().equals("Herbivore"))
				{
					
					if(((a1.get_x()*a1.get_x())+(a1.get_y()*a1.get_y()))<((a2.get_x()*a2.get_x())+(a2.get_y()*a2.get_y())))
					{
						return -1;
					}
					else
					{
						return 1;
					}
				}
				else
				{
					if(((a1.get_x()*a1.get_x())+(a1.get_y()*a1.get_y()))<((a2.get_x()*a2.get_x())+(a2.get_y()*a2.get_y())))
					{
						return -1;
					}
					else
						return 1;
				}
			}
		}
	}
}

class World
{
	
	Scanner sc=new Scanner(System.in);
	int total_time,health_herb,grass_cap,health_carn,turns=0;
	Animal a;
	int carnivore_count=2,herbivore_count=2;
	double x;
	int max;
	void input()
	{
		max=0;
		System.out.println("Enter Total Final Time for Simulation:");
		total_time=sc.nextInt();
		
		System.out.println("Enter x, y centre, radius and Grass Available for First Grassland:");
		Grassland g1=new Grassland(sc.nextInt(),sc.nextInt(),sc.nextInt(),sc.nextInt());
		
		System.out.println("Enter x, y centre, radius and Grass Available for Second Grassland:");
		Grassland g2=new Grassland(sc.nextInt(),sc.nextInt(),sc.nextInt(),sc.nextInt());
		
		System.out.println("Enter Health and Grass Capacity for Herbivores:");
		health_herb=sc.nextInt();
		grass_cap=sc.nextInt();
		
		System.out.println("Enter x, y position and timestamp for First Herbivore:");
		Herbivore h1=new Herbivore(health_herb,grass_cap,sc.nextInt(),sc.nextInt(),sc.nextInt(),"First Herbivore");
		max=h1.get_timestamp();
		
		System.out.println("Enter x, y position and timestamp for Second Herbivore:");
		health_herb=sc.nextInt();
		grass_cap=sc.nextInt();
		Herbivore h2=new Herbivore(health_herb,grass_cap,sc.nextInt(),sc.nextInt(),sc.nextInt(),"Second Herbivore");
		if(max<h2.get_timestamp())
		{
			max=h2.get_timestamp();
		}
		
		System.out.println("Enter Health for Carnivores:");
		health_carn=sc.nextInt();
		
		System.out.println("Enter x, y position and timestamp for First Carnivore:");
		Carnivore c1=new Carnivore(health_carn,sc.nextInt(),sc.nextInt(),sc.nextInt(),"First Carnivore");
		if(max<c1.get_timestamp())
		{
			max=c1.get_timestamp();
		}
		
		System.out.println("Enter x, y position and timestamp for Second Carnivore:");
		Carnivore c2=new Carnivore(health_carn,sc.nextInt(),sc.nextInt(),sc.nextInt(),"Second Carnivore");
		if(max<c2.get_timestamp())
		{
			max=c2.get_timestamp();
		}
		
		System.out.println("The Simulation Begins -");
		
		PriorityQueue<Animal> queue=new PriorityQueue<Animal>(4, new Compare());
		queue.add(h1);
		queue.add(h2);
		queue.add(c1);
		queue.add(c2);
		Random rand= new Random();
		if(!queue.isEmpty() && turns<=total_time)
		{
			a=queue.remove();
			turns++;
			if(a.getClass().getName().equals("Carnivore"))
			{
				carnivore_count--;
				a.take_turn(herbivore_count, g1, g2, h1, h2, queue);
				System.out.println("It is "+a.name);
				if(a.health>0)
				{
					int new_time=rand.nextInt(total_time-max) + max;
					if(new_time<total_time-1)
					{
						a.time_stamp=new_time;
						queue.add(a);
						carnivore_count++;
						System.out.println("Its health after taking turn is "+a.health);
					}
				}
				else
				{
					System.out.println("It is dead");
				}
			}
		}
	}
	
}

abstract class Animal
{
	protected int health,x,y,time_stamp,life=1;
	protected String name;
	public int get_health()
	{
		return health;
	}
	public int get_x()
	{
		return x;
	}
	public int get_y()
	{
		return y;
	}
	public int get_timestamp()
	{
		return time_stamp;
	}
	
	abstract void take_turn(int count, Grassland g1, Grassland g2, Animal a1, Animal a2, PriorityQueue q);
	
	boolean inside_grassland(Grassland g)
	{
		if((this.get_x()-g.get_x())*(this.get_x()-g.get_x())+(this.get_y()-g.get_y())*(this.get_y()-g.get_y())-(g.get_radius()*g.get_radius())<=0)
			return true;
		else
			return false;
	}
	
}

class Herbivore extends Animal
{
	protected int grass_capacity;
	
	Herbivore(int health,int grass_capacity,int x,int y,int time_stamp, String name)
	{
		this.x=x;
		this.y=y;
		this.time_stamp=time_stamp;
		this.grass_capacity=grass_capacity;
		this.health=health;
		this.life=1;
		this.name=name;
	}
	
	public int get_grasscapacity()
	{
		return grass_capacity;
	}
	
	void take_turn(int count, Grassland g1, Grassland g2, Animal a1, Animal a2, PriorityQueue q)
	{
		
	}
}


class Carnivore extends Animal
{
	int d,d1,d2;
	
	Carnivore(int health,int x,int y,int time_stamp, String name)
	{
		this.x=x;
		this.y=y;
		this.time_stamp=time_stamp;
		this.health=health;
		this.life=1;
		this.name=name;
	}
	
	double cos(Animal h)
	{
		double cos = (this.get_x()-h.get_x())/(this.get_x()-h.get_x())*(this.get_x()-h.get_x())+(this.get_y()-h.get_y())*(this.get_y()-h.get_y());
		return cos;
	}
	
	double sin(Animal h)
	{
		double sin = (this.get_y()-h.get_y())/(this.get_x()-h.get_x())*(this.get_x()-h.get_x())+(this.get_y()-h.get_y())*(this.get_y()-h.get_y());
		return sin;
	}
	
	int get_distance(Animal a)
	{
		d=(this.get_x()-a.get_x())*(this.get_x()-a.get_x())+(this.get_y()-a.get_y())*(this.get_y()-a.get_y());
		return d;
	}
	
	void towards_herb(Animal h1, Animal h2, int r)
	{
		int d1=this.get_distance(h1);
		int d2= this.get_distance(h2);
		if(d1<d2)
		{
			this.x=(int) ((int)r*this.cos(h1))+this.x;
			this.y=(int) ((int)r*this.cos(h1))+this.y;
		}
		if(d1>=d2)
		{
			this.x=(int) ((int)r*this.cos(h2))+this.x;
			this.y=(int) ((int)r*this.cos(h2))+this.y;
		}
	}	
	void take_turn(int h_count, Grassland g1, Grassland g2, Animal a1, Animal a2, PriorityQueue q)
	{
		if(h_count!=0)
		{
			d1=this.get_distance(a1);
			d2=this.get_distance(a2);
			if(d1<=1 && d2<=1)
			{
				if(d1<d2)
				{
					a1.life=0;
					q.remove(a1);
					this.health+=(2/3)*a1.health;
				}
				else
				{
					a2.life=0;
					q.remove(a2);
					this.health+=(2/3)*a2.health;
				}
			}
			else if(d1<=1)
			{
				a1.life=0;
				q.remove(a1);
				this.health+=(2/3)*a1.health;
			}
			else if(d2<=1)
			{
				a2.life=0;
				q.remove(a2);
				this.health+=(2/3)*a2.health;
			}
			else
			{
				double s=Math.random()*100;
				if(this.inside_grassland(g1) || this.inside_grassland(g2))
				{
					if(s>=75)
					{
						this.health-=30;
					}
					else
					{
						this.towards_herb(a1, a2, 2);
					}
				}
				else
				{
					if(s<92)
					{
						this.towards_herb(a1, a2, 4);
					}
					else
					{
						this.health-=60;
					}
				}
			}
		}
	}
}

class Grassland
{
	private int x,y;
	private int r;
	private int grass_availability;
	
	Grassland(int x,int y,int r,int grass_availability)
	{
		this.x=x;
		this.y=y;
		this.r=r;
		this.grass_availability=grass_availability;
	}
	
	public int get_x()
	{
		return x;
	}
	public int get_y()
	{
		return y;
	}
	public int get_radius()
	{
		return r;
	}
	public int get_grass_availability()
	{
		return grass_availability;
	}
}
