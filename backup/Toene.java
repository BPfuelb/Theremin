import java.util.Arrays;
import java.util.HashMap;

public class Toene {

	public static float __A = 27.5000f;
	public static float __Ais___B = 29.1353f;
	public static float __H = 30.8677f;
	public static float _C = 32.7032f;
	public static float _Cis__Des = 34.6479f;
	public static float _D = 36.7081f;
	public static float _Dis__Es = 38.8909f;
	public static float _E = 41.2035f;
	public static float _F = 43.6536f;
	public static float _Fis__Ges = 46.2493f;
	public static float _G = 48.9995f;
	public static float _Gis__As = 51.9130f;
	public static float _A = 55.0000f;
	public static float _Ais__B = 58.2705f;
	public static float _H = 61.7354f;
	public static float C = 65.4064f;
	public static float Cis_Des = 69.2957f;
	public static float D = 73.4162f;
	public static float Dis_Es = 77.7817f;
	public static float E = 82.4069f;
	public static float F = 87.3071f;
	public static float Fis_Ges = 92.4986f;
	public static float G = 97.9989f;
	public static float Gis_As = 103.826f;
	public static float A = 110.000f;
	public static float Ais_B = 116.541f;
	public static float H = 123.471f;
	public static float c_ = 130.813f;
	public static float cis_des = 138.591f;
	public static float d_ = 146.832f;
	public static float dis_es = 155.563f;
	public static float e_ = 164.814f;
	public static float f_ = 174.614f;
	public static float fis_ges = 184.997f;
	public static float g_ = 195.998f;
	public static float gis_as = 207.652f;
	public static float a_ = 220.000f;
	public static float ais_b = 233.082f;
	public static float h_ = 246.942f;
	public static float c_T = 261.626f;
	public static float cis_T_des_T = 277.183f;
	public static float d_T = 293.665f;
	public static float dis_T_es_T = 311.127f;
	public static float e_T = 329.628f;
	public static float f_T = 349.228f;
	public static float fis_T_ges_T = 369.994f;
	public static float g_T = 391.995f;
	public static float gis_T_as_T = 415.305f;
	public static float a_T = 440.000f;
	public static float ais_T_b_T = 466.164f;
	public static float h_T = 493.883f;
	public static float c_T_T = 523.251f;
	public static float cis_T_T_des_T_T = 554.365f;
	public static float d_T_T = 587.330f;
	public static float dis_T_T_es_T_T = 622.254f;
	public static float e_T_T = 659.255f;
	public static float f_T_T = 698.456f;
	public static float fis_T_T_ges_T_T = 739.989f;
	public static float g_T_T = 783.991f;
	public static float gis_T_T_as_T_T = 830.609f;
	public static float a_T_T = 880.000f;
	public static float ais_T_T_b_T_T = 932.328f;
	public static float h_T_T = 987.767f;
	public static float c_T_T_T = 1046.50f;
	public static float cis_T_T_T_des_T_T_T = 1108.73f;
	public static float d_T_T_T = 1174.66f;
	public static float dis_T_T_T_es_T_T_T = 1244.51f;
	public static float e_T_T_T = 1318.51f;
	public static float f_T_T_T = 1396.91f;
	public static float fis_T_T_T_ges_T_T_T = 1479.98f;
	public static float g_T_T_T = 1567.98f;
	public static float gis_T_T_T_as_T_T_T = 1661.22f;
	public static float a_T_T_T = 1760.00f;
	public static float ais_T_T_T_b_T_T_T = 1864.66f;
	public static float h_T_T_T = 1975.53f;
	public static float c_T_T_T_T = 2093.00f;
	public static float cis_T_T_T_T_des_T_T_T_T = 2217.46f;
	public static float d_T_T_T_T = 2349.32f;
	public static float dis_T_T_T_T_es_T_T_T_T = 2489.02f;
	public static float e_T_T_T_T = 2637.02f;
	public static float f_T_T_T_T = 2793.83f;
	public static float fis_T_T_T_T_ges_T_T_T_T = 2959.96f;
	public static float g_T_T_T_T = 3135.96f;
	public static float gis_T_T_T_T_as_T_T_T_T = 3322.44f;
	public static float a_T_T_T_T = 3520.00f;
	public static float ais_T_T_T_T_b_T_T_T_T = 3729.31f;
	public static float h_T_T_T_T = 3951.07f;
	public static float c_T_T_T_T_T = 4186.01f;

	public Float[] frequenzen = { 27.5000f, 29.1353f, 30.8677f, 32.7032f, 34.6479f,
			36.7081f, 38.8909f, 41.2035f, 43.6536f, 46.2493f, 48.9995f,
			51.9130f, 55.0000f, 58.2705f, 61.7354f, 65.4064f, 69.2957f,
			73.4162f, 77.7817f, 82.4069f, 87.3071f, 92.4986f, 97.9989f,
			103.826f, 110.000f, 116.541f, 123.471f, 130.813f, 138.591f,
			146.832f, 155.563f, 164.814f, 174.614f, 184.997f, 195.998f,
			207.652f, 220.000f, 233.082f, 246.942f, 261.626f, 277.183f,
			293.665f, 311.127f, 329.628f, 349.228f, 369.994f, 391.995f,
			415.305f, 440.000f, 466.164f, 493.883f, 523.251f, 554.365f,
			587.330f, 622.254f, 659.255f, 698.456f, 739.989f, 783.991f,
			830.609f, 880.000f, 932.328f, 987.767f, 1046.50f, 1108.73f,
			1174.66f, 1244.51f, 1318.51f, 1396.91f, 1479.98f, 1567.98f,
			1661.22f, 1760.00f, 1864.66f, 1975.53f, 2093.00f, 2217.46f,
			2349.32f, 2489.02f, 2637.02f, 2793.83f, 2959.96f, 3135.96f,
			3322.44f, 3520.00f, 3729.31f, 3951.07f, 4186.01f };
	
	
		



	
	public HashMap<String, Float> toeneFreq = new HashMap<>();
	
	public double[] freqSelbst = new double[88];
	
	public Toene()
	{
		for(int i = 0; i < frequenzen.length; i++)
		{
			frequToene.put(frequenzen[i], toene[i]);
			toeneFreq.put(toene[i], frequenzen[i]);
		}
	}
	
	
	
}
