
    package KNN; 
    
    public class KNNNode { 
        private int index; 
        private double distance; 
        private int c;
        public KNNNode(int index, double distance, int c) { 
            super(); 
            this.index = index; 
            this.distance = distance; 
            this.c = c; 
        } 
         
         
        public int getIndex() { 
            return index; 
        } 
        public void setIndex(int index) { 
            this.index = index; 
        } 
        public double getDistance() { 
            return distance; 
        } 
        public void setDistance(double distance) { 
            this.distance = distance; 
        } 
        public int getC() { 
            return c; 
        } 
        public void setC(int c) { 
            this.c = c; 
        } 
    }  