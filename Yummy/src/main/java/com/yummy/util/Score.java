package com.yummy.util;

public class Score {
    private static int[] MemberScores = new int[]{0, 200, 500, 1000, 2000};
    private static double[] MerchantScores = new double[]{0, 200, 500,1000};

    public static int getNextScore(int num){
        for (int i=0;i<MemberScores.length;i++){
            if (num<MemberScores[i])
                return MemberScores[i];
        }
        return 2000;
    }

    public static int getLevel(int score){
        for (int i=0;i<MemberScores.length;i++){
            if (score<MemberScores[i])
                return i;
        }
        return 5;
    }
}
