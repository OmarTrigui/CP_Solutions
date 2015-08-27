#include <iostream>
#include <string>

// https://www.hackerrank.com/challenges/sansa-and-xor
// Sansa and XOR
// Author : Omar Trigui


using namespace std;

int main() {

	ios::sync_with_stdio(0);
	cin.tie(0);
	cout.tie(0);

	int n, nb, item;
	cin >> n;

	for (int i = 0; i < n; i++) {

		cin >> nb;

		int result = 0;

		for (int j = 0; j < nb; j++) {
			cin >> item;
			if ((j & 1) == 0) {
				result ^= item;
			}
		}

		if ((nb & 1) == 0)
			result ^= result;

		cout << result << endl;
	}

}

