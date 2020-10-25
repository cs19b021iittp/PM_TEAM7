import java.util.*;

class Customer {
	int id;
	public int vehiclesize;
	public String VehicleType;
	public String typeofspot;
	Scanner sc = new Scanner(System.in);

	Customer(int id, String vehicletype) {
		this.id = id;
		this.VehicleType = vehicletype;
		setvehiclesize();
		setspotType();
	}

	public String getVehicletype() {
		return this.VehicleType;
	}

	public int getid() {
		return this.id;
	}

	public void setvehiclesize() {
		if (getVehicletype().equals("motorcycle"))
			this.vehiclesize = 1;
		if (getVehicletype().equals("car"))
			this.vehiclesize = 2;
		if (getVehicletype().equals("truck"))
			this.vehiclesize = 3;
		// return this.vehiclesize;
	}

	public void setspotType() {
		if (this.vehiclesize == 1)
			this.typeofspot = "small";
		if (this.vehiclesize == 2)
			this.typeofspot = "compact";
		if (this.vehiclesize == 3)
			this.typeofspot = "large";
	}

	public int getvehiclesize() {
		return this.vehiclesize;
	}

	private long hours;

	public long gettime() {
		Scanner sc = new Scanner(System.in);
		this.hours = sc.nextLong();
		return this.hours;
	}
}

class Ticket {
	public int challan = 0;

	void ticket() {
		this.challan++;
	}
}

class Spot {
	public int value = 1;
	private int spotNo;
	private boolean isElectric;
	public boolean isReserved;
	public int vehiclesize;
	public String typeofspot;

	public Spot(int spotNo, boolean isElectric, String typeofspot) {
		this.spotNo = spotNo;
		this.isElectric = isElectric;
		this.isReserved = false;
		this.typeofspot = typeofspot;

	}

	public int getSpotNo() {
		return this.spotNo;
	}

	public boolean isElectric() {
		return this.isElectric;
	}

	public void setElectric(boolean status) {
		this.isElectric = status;
	}

	public boolean isReserved() {
		return this.isReserved;
	}

	public void setReservedStatus(boolean status) {
		this.isReserved = status;
	}

	public void setvehiclesize() {
		if (getspotType().equals("motorcycle"))
			this.vehiclesize = 1;
		if (getspotType().equals("car"))
			this.vehiclesize = 2;
		if (getspotType().equals("truck"))
			this.vehiclesize = 3;
		// return this.vehiclesize;
	}

	public void setspotType() {
		if (this.vehiclesize == 1)
			this.typeofspot = "small";
		if (this.vehiclesize == 2)
			this.typeofspot = "compact";
		if (this.vehiclesize == 3)
			this.typeofspot = "large";
	}

	public String getspotType() {
		return this.typeofspot;
	}
}

class Floor {
	public ArrayList<Spot> pl = new ArrayList<Spot>(15);
	public ArrayList<Customer> cl = new ArrayList<Customer>();
	public Spot[] p;
	private int allotedspot;

	// private String typeofspot;
	public Floor() {
		this.p = new Spot[15];
		for (int i = 0; i < 15; i++) {
			// pl.add(new Spot(i+1,false));
			if (i >= 0 && i < 3)
				this.p[i] = new Spot(i + 1, false, "small");
			if (i >= 3 && i < 5)
				this.p[i] = new Spot(i + 1, true, "small");
			if (i >= 5 && i < 8)
				this.p[i] = new Spot(i + 1, false, "compact");
			if (i >= 8 && i < 10)
				this.p[i] = new Spot(i + 1, true, "compact");
			if (i >= 10 && i < 13)
				this.p[i] = new Spot(i + 1, false, "large");
			if (i >= 13 && i < 15)
				this.p[i] = new Spot(i + 1, true, "large");
		}

	}

	// let a floor contains 15 spots
	Spot[] getAvailablespots() {
		for (int i = 0; i < 15; i++) {
			if (!p[i].isReserved()) {
				this.pl.add(p[i]);
			}
		}
		return this.pl.toArray(new Spot[0]);
	}

	public Spot[] getallspots() {
		return p;
	}

	int display(int fno) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Are you a new customer(enter 1) or leaving customer(enter 2) ");
		int opt = sc.nextInt();
		// System.out.println("enter vehicletype");
		// String vehicletype=sc.nextLine();
		if (opt == 1) {
			System.out.println("enter vehicletype");
			String vehicletype = sc.next();
			Customer c = new Customer(-1, vehicletype);
			System.out.println("Is your vehicle electrical(true/false)?");
			boolean ele = sc.nextBoolean();
			for (int j = 0; j < 5; j++) {
				System.out.println("on floor " + (j + 1) + " :");

				for (int k = 0; k < 15; k++) {
					if (!p[k].isReserved()) {
						if (p[k].isElectric()) {
							System.out.println("spot is available at spot number " + (k + 1) + "  which is electrical "
									+ " and " + p[k].getspotType());
						} else {
							System.out.println(
									"spot is available at spot number " + (k + 1) + "  and  " + p[k].getspotType());
						}
					}
				}
			}

			int i = 0;
			for (i = 0; i < 15; i++) {
				if (!p[i].isReserved() && p[i].getspotType().equals(c.typeofspot) && p[i].isElectric() == ele) {
					c.id = i;
					System.out.println("on the floor " +(fno+1) + " you can go to " + (i + 1) + " spot");
					cl.add(c);
					this.p[i].isReserved = true;
					return 0;
				}
			}
			return 1;
		}

		if (opt == 2) {
			System.out.println("tell us ur spot number that we have assigned:");
			int id = sc.nextInt();
			p[id].isReserved = false;
			System.out.println("We have 3 exits");
			int hours;
			long bill = 0;
			System.out.println("enter no. of hours the vehicle is parked");
			hours = sc.nextInt();
			if (!p[id].isElectric()) {
				if (hours <= 1) {
					bill = hours * 20;
				} else if (hours > 1 && hours <= 3) {
					bill = 20 + (hours - 1) * 10;
				} else if (hours > 3) {
					bill = 40 + (hours - 3) * 5;// (20+ 10 + 10 )
				} else
					System.out.println("wrong values of hours");
				System.out.println("it is parked for" + hours + " hours");
				System.out.println("Total amount to be paid is Rs." + bill);
			} 
			if(p[id].isElectric()){
				
				if (hours <= 1) {
					bill = hours * (20 + 10);// extra 10 rupees for electric charge
				} else if (hours > 1 && hours <= 3) {
					bill = (20 + 10) + (hours - 1) * (10 + 10);
				} else if (hours > 3) {
					bill = (40 + 10) + (hours - 3) * (5 + 10);// (20+ 10 + 10 ) and (5+10) extra 10 for electric charge
					//System.out.println("it is parked for" + hours + " hours");
					//System.out.println("Total amount to be paid is Rs." + bill);
				}
				System.out.println("it is parked for" + hours + " hours");
				System.out.println("Total amount to be paid is Rs." + bill);

			}
			return 0;
		} // use payment thing.
		return 0;
	}
}

public class Parking_Lot {
	public static void main(String[] args) {
		Floor[] f = new Floor[5];
		for (int i = 0; i < 5; i++) {
			f[i] = new Floor();
		}
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("Welcome to company_7 Parkinglot");
			int cnt =0;
			for (int i = 0; i < 5; i++) {
				cnt+=f[i].display(i);
			}
			if(cnt==5){
				System.out.println("Sorry, No space for you");
			}
			System.out.println("input 0 if u want to leave the parking.");
			int op = sc.nextInt();
			if (op == 0)
				break;
		}

	}
}