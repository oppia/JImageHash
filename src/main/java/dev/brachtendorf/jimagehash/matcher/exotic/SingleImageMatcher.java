package dev.brachtendorf.jimagehash.matcher.exotic;

import android.graphics.Bitmap;
import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;

import dev.brachtendorf.jimagehash.hash.Hash;
import dev.brachtendorf.jimagehash.hashAlgorithms.HashingAlgorithm;
import dev.brachtendorf.jimagehash.matcher.TypedImageMatcher;

/**
 * Convenience class to chain multiple hashing algorithms to check if two images
 * are similar. In order for images to be considered similar all supplied
 * hashing algorithms have to agree that the images match.
 * 
 * @author Kilian
 *
 */
public class SingleImageMatcher extends TypedImageMatcher {
	/**
	 * Execute all supplied hashing algorithms in the order they were supplied and
	 * check if the images are similar
	 * 
	 * @param image  First input image
	 * @param image1 Second input image
	 * @return true if images are considered similar
	 */
	public boolean checkSimilarity(Bitmap image, Bitmap image1) {
		if (steps.isEmpty())
			throw new IllegalStateException(
					"Please supply at least one hashing algorithm prior to invoking the match method");

		for (Entry<HashingAlgorithm, AlgoSettings> entry : steps.entrySet()) {
			Hash hash = entry.getKey().hash(image);
			Hash hash1 = entry.getKey().hash(image1);

			// Check if the hashing algo is within the threshold. If it's not return early
			if (!entry.getValue().apply(hash, hash1)) {
				return false;
			}
		}
		// Everything matched
		return true;
	}

}
