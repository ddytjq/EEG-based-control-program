public class Normalization {
	
	private int numOfPoints;
	private int numOfDimension;
	private double[][] dataset;
	private double[][] minMaxNormalizedDataset;



	public Normalization(double[][] dataset2, int nPoints, int nDimensions) {
			dataset = dataset2;
			numOfPoints = nPoints;
			numOfDimension = nDimensions;
		}



	public double[][] getMinMaxNormalizedDataset() {
		return minMaxNormalizedDataset;
	}

	public void setMinMaxNormalizedDataset(double[][] minMaxNormalizedDataset) {
		this.minMaxNormalizedDataset = minMaxNormalizedDataset;
	}


	
	// Min-Max Normalization
	public double[][] minMaxNormalization(double[][] datasetF) {
		minMaxNormalizedDataset = new double[numOfPoints][];

		for (int i = 0; i < numOfPoints; i++) {
			minMaxNormalizedDataset[i] = new double[numOfDimension];
		}

		double min = minimum(dataset);
		double max = maximum(dataset);
		double new_min = 0;
		double new_max = 1;
		for (int i = 0; i < numOfPoints; i++) {
			for (int j = 0; j < numOfDimension; j++) {
				minMaxNormalizedDataset[i][j] = ((datasetF[i][j] - min) / (max - min)) * (new_max - new_min) + new_min;
			}
		}
		 printFile(minMaxNormalizedDataset);
		return minMaxNormalizedDataset;
	}

	
	
	// min value in point (column)
	public double minimum(double[][] datasetF) {
		int col = 0;
		double minF = Double.MAX_VALUE;
		while (col != numOfDimension) {
			minF = Double.MAX_VALUE;
			for (int i = 0; i < datasetF.length; i++) {
				if (datasetF[i][col] < minF)
					minF = datasetF[i][col];
			}
			// System.out.println("Minimum "+col+" = " + minF);
			col++;
		}
		return minF;
	}

	
	
	// max value in point (column)
	public double maximum(double[][] datasetF) {
		int col = 0;
		double maxF = -(Double.MIN_VALUE);
		while (col != numOfDimension) {
			maxF = -(Double.MIN_VALUE);
			for (int i = 0; i < datasetF.length; i++) {
				if (datasetF[i][col] > maxF)
					maxF = datasetF[i][col];
			}
			// System.out.println("Maximum = " + maxF);
			col++;
		}
		return maxF;
	}



	public void printFile(double[][] datasetF) {
		// print multi-dimensional array
		for (int i = 0; i < numOfPoints; i++) {
			for (int j = 0; j < numOfDimension; j++) {
				System.out.print(datasetF[i][j] + " ");
			}
			System.out.println();
		}
	}

}