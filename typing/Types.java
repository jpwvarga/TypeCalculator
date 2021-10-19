package typing;

import java.util.*;
import javafx.scene.paint.Color;

public class Types
{	
	public static final int NUM_TYPES = 18;
	public static final float IMMUNE2 = 0F;
	public static final float RESISTS = 0.5F;
	public static final float NORMALEFF = 1F;
	public static final float WEAK2 = 2F;
	
	public static final Color[] TYPE_COLORS =
		{
				Color.web("68A090"), // ???
				Color.web("A8A878"), // NORMAL
				Color.web("F08030"), // FIRE
				Color.web("6890F0"), // WATER
				Color.web("78C850"), // GRASS
				Color.web("F8D030"), // ELECTRIC
				Color.web("98D8D8"), // ICE
				Color.web("C03028"), // FIGHTING
				Color.web("A040A0"), // POISON
				Color.web("E0C068"), // GROUND
				Color.web("A890F0"), // FLYING
				Color.web("F85888"), // PSYCHIC
				Color.web("A8B820"), // BUG
				Color.web("B8A038"), // ROCK
				Color.web("705898"), // GHOST
				Color.web("7038F8"), // DRAGON
				Color.web("705848"), // DARK
				Color.web("B8B8D0"), // STEEL
				Color.web("EE99AC")  // FAIRY
		};
	
	public static enum Type 
	{
		NIL("???", -1), 
		NML("Normal", 0), 
		FRE("Fire", 1), 
		WAT("Water", 2),
		GRA("Grass", 3), 
		ELE("Electric", 4), 
		ICE("Ice", 5), 
		FIT("Fighting", 6), 
		POI("Poison", 7),
		GND("Ground", 8), 
		FLY("Flying", 9), 
		PSY("Psychic", 10), 
		BUG("Bug", 11),
		RCK("Rock", 12), 
		GHO("Ghost", 13), 
		DGN("Dragon", 14),
		DRK("Dark", 15), 
		STL("Steel", 16), 
		FAI("Fairy", 17);
		
		private final String fullName;
		private final int index;
		
		private final Color color;
		
		private final ArrayList<String> WEAKNESSES;
		private final ArrayList<String> RESISTANCES;
		private final ArrayList<String> IMMUNITIES;
		
		
		// Constructors
		private Type(String name, int i, String[] weaknesses, String[] resistances, String[] immunities)
		{
			fullName = name;
			index = i;
			
			int n = index + 1;
			if(n > 0 && n < TYPE_COLORS.length)
				color = TYPE_COLORS[n];
			else
				color = TYPE_COLORS[0];
			
			WEAKNESSES = new ArrayList<String>();
			RESISTANCES	= new ArrayList<String>();
			IMMUNITIES = new ArrayList<String>();
			
			for (String wk: weaknesses)
				WEAKNESSES.add(wk);
			
			for (String rt: resistances)
				RESISTANCES.add(rt);
			
			for (String im: immunities)
				IMMUNITIES.add(im);
		}
		
		private Type(String name, int i)
		{
			fullName = name;
			index = i;
			
			int n = index + 1;
			if(n > 0 && n < TYPE_COLORS.length)
				color = TYPE_COLORS[n];
			else
				color = TYPE_COLORS[0];
			
			WEAKNESSES = new ArrayList<String>();
			RESISTANCES	= new ArrayList<String>();
			IMMUNITIES = new ArrayList<String>();
		}

		// Static functions
		public static Type valueOfName(String name)
		{
			for(Type e: values())
				if (e.toString().compareToIgnoreCase(name) == 0)
					return e;
			return NIL;
		}
		
		public static Type valueOfIndex(int i)
		{
			for(Type e: values())
				if (e.index == i)
					return e;
			return NIL;
		}
		
		// Getters
		public Color getColor()
		{
			return color;
		}
		
		public ArrayList<Type> getWeaknesses()
		{
			ArrayList<Type> vuls = new ArrayList<Type>();
			
			for (int i = 0; i < WEAKNESSES.size();)
			{
				String vul = WEAKNESSES.get(i);
				Type t = valueOfName(vul);
				
				if (t == NIL)
					WEAKNESSES.remove(i);
				else
				{
					vuls.add(t);
					i++;
				}
			}
			
			return vuls;
		}
		
		public ArrayList<Type> getResistances()
		{
			ArrayList<Type> rsts = new ArrayList<Type>();
			
			for (int i = 0; i < RESISTANCES.size();)
			{
				String rst = RESISTANCES.get(i);
				Type t = valueOfName(rst);
				
				if (t == NIL)
					RESISTANCES.remove(i);
				else
				{
					rsts.add(t);
					i++;
				}
			}
			
			return rsts;
		}
		
		public ArrayList<Type> getImmunities()
		{
			ArrayList<Type> imms = new ArrayList<Type>();
			
			for (int i = 0; i < IMMUNITIES.size();)
			{
				String imm = IMMUNITIES.get(i);
				Type t = valueOfName(imm);
				
				if (t == NIL)
					IMMUNITIES.remove(i);
				else
				{
					imms.add(t);
					i++;
				}
			}
			
			return imms;
		}
		
		// Add type for chart
		public void addWeaknesses(Type... types)
		{
			for (Type t: types)
			{
				String s = t.fullName;
				boolean present = false;
				
				for (String str: WEAKNESSES)
					if (str.compareToIgnoreCase(s) == 0)
						present = true;
				
				if (t != NIL && !present)
				{
					WEAKNESSES.add(t.fullName);
					RESISTANCES.remove(t.fullName);
					IMMUNITIES.remove(t.fullName);
				}
			}
		}
		
		public void addResistances(Type... types)
		{
			for (Type t: types)
			{
				String s = t.fullName;
				boolean present = false;
				
				for (String str: RESISTANCES)
					if (str.compareToIgnoreCase(s) == 0)
						present = true;
				
				if (t != NIL && !present)
				{
					WEAKNESSES.remove(s);
					RESISTANCES.add(s);
					IMMUNITIES.remove(s);
				}
			}
		}
		
		public void addImmunities(Type... types)
		{
			for (Type t: types)
			{
				String s = t.fullName;
				boolean present = false;
				
				for (String str: IMMUNITIES)
					if (str.compareToIgnoreCase(s) == 0)
						present = true;
				
				if (t != NIL && !present)
				{
					WEAKNESSES.remove(t.fullName);
					RESISTANCES.remove(t.fullName);
					IMMUNITIES.add(t.fullName);
				}
			}
		}
		
		// Private functions
		private Hashtable<Type,Float> getMux()
		{
			Hashtable<Type,Float> mux = new Hashtable<Type,Float>();
			
			switch(this)
			{
				case NML:
					addImmunities(GHO);
					addWeaknesses(FIT);
					break;
					
				case FRE:
					addWeaknesses(GND,WAT,RCK);
					addResistances(BUG,FAI,FRE,GRA,ICE,STL);
					break;
					
				case WAT:
					addWeaknesses(ELE,GRA);
					addResistances(FRE,ICE,STL,WAT);
					break;
					
				case GRA:
					addWeaknesses(BUG,FRE,FLY,ICE,POI);
					addResistances(ELE,GRA,GND,WAT);
					break;
					
				case ELE:
					addWeaknesses(GND);
					addResistances(ELE,FLY,STL);
					break;
				
				case ICE: 
					addWeaknesses(FIT,FRE,RCK,STL);
					addResistances(ICE);
					break;
					
				case FIT: 
					addResistances(BUG,DRK,RCK);
					addWeaknesses(FAI,FLY,PSY);
					break;
					
				case POI:
					addResistances(FIT,POI,BUG,GRA,FAI);
					addWeaknesses(GND,PSY);
					break;
				
				case GND:
					addImmunities(ELE);
					addResistances(POI,RCK);
					addWeaknesses(GRA,ICE,WAT);
					break;
				
				case FLY: 
					addImmunities(GND);
					addResistances(BUG,FIT,GRA);
					addWeaknesses(ELE,ICE,RCK);
					break;
				
				case PSY: 
					addResistances(FIT,PSY);
					addWeaknesses(BUG,DRK,GHO);
					break;
					
				case BUG:
					addResistances(FIT,GRA,GND);
					addWeaknesses(FRE,FLY,RCK);
					break;
					
				case RCK: 
					addResistances(FRE,FLY,NML,POI);
					addWeaknesses(FIT,GRA,GND,STL,WAT);
					break;
					
				case GHO:
					addImmunities(NML,FIT);
					addResistances(BUG,POI);
					addWeaknesses(DRK,GHO);
					break;
					
				case DGN:
					addResistances(ELE,FRE,GRA,WAT);
					addWeaknesses(DGN,FAI,ICE);
					break;				
					
				case DRK:
					addImmunities(PSY);
					addResistances(DRK,GHO);
					addWeaknesses(BUG,FAI,FIT);
					break;
					
				case STL:
					addImmunities(POI);
					addResistances(BUG,DGN,FAI,FLY,GRA,ICE,NML,PSY,RCK,STL);
					addWeaknesses(FIT,FRE,GND);
					break;
					
				case FAI: 
					addImmunities(DGN);
					addResistances(BUG,DRK,FIT);
					addWeaknesses(POI,STL);
					break;
				
				default:
					break;
			}
			
			for (Type vul: getWeaknesses())
				mux.put(vul, WEAK2);
			
			for (Type rst: getResistances())
				mux.put(rst, RESISTS);
			
			for (Type imm: getImmunities())
				mux.put(imm, IMMUNE2);		
			
			for (Type t: values())
				mux.putIfAbsent(t, NORMALEFF);
			
			return mux;
		}
		
		@Override
		public String toString()
		{
			return fullName;
		}
	}
	
	public static Hashtable<Type,Float> getDefMux(Type... defTypes)
	{
		Hashtable<Type,Float> defMux = new Hashtable<Type,Float>();
		
		for (Type t: Type.values())
		{
			float m = 1.0f;
			
			for (Type dT: defTypes)
				m *= dT.getMux().get(t); // TODO: Remove non-unique types
			
			defMux.put(t, m);
		}
		
		return defMux;
	}
	
	public static Hashtable<Type,Float> getDefMux(Collection<Type> defTypes)
	{
		Type types[] = new Type[defTypes.size()];
		
		defTypes.toArray(types);
		
		return getDefMux(types);
	}
	
	public static void main(String[] args)
	{
		for (int i = 0; i < NUM_TYPES; i++)
		{
			Type defType1 = Type.valueOfIndex(i);
			
			for (int j = i; j < NUM_TYPES; j++)
			{
				Type defType2 = Type.valueOfIndex(j);
				
				System.out.printf("Against %s", defType1.toString());
				
				if(defType1 == defType2)
				{
					defType2 = Type.NIL;	
					System.out.printf(" Monotype:%n");
				}
				else
				{
					System.out.printf("-%s:%n", defType2.toString());
				}
				
				Hashtable<Type,Float> mux = Types.getDefMux(defType1, defType2);
				
				for (int k = 0; k < NUM_TYPES; k++)
				{
					Type atkType = Type.valueOfIndex(k);
					
					System.out.printf(" %-10s\t:\tx%s%n", atkType.toString(), mux.get(atkType));
				}
				
				System.out.println();
			}
		}
	}
}