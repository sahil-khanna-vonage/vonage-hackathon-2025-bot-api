CREATE TYPE queue_enum AS ENUM ('Product Support', 'Billing Support', 'Technical Support');
CREATE TYPE status_enum AS ENUM ('completed', 'dropped', 'queued', 'on going', 'missed');
CREATE TYPE call_type_enum AS ENUM ('inbound', 'outbound');

CREATE TABLE agents (
	agent_id INT PRIMARY KEY,
	agent_name VARCHAR(100)
);

CREATE TABLE agent_status (
	agent_id INT PRIMARY KEY,
	status VARCHAR(20),
	logged_in_at TIMESTAMP,
	on_break_since TIMESTAMP,
	FOREIGN KEY (agent_id) REFERENCES agents(agent_id)
);

CREATE TABLE agent_performance (
	agent_id INT PRIMARY KEY,
	calls_handled INT,
	aht FLOAT,
	break_time FLOAT,
	csat FLOAT,
	login_duration FLOAT,
	FOREIGN KEY (agent_id) REFERENCES agents(agent_id)
);

CREATE TABLE queue_status (
	queue_name queue_enum PRIMARY KEY,
	waiting_calls INT,
	average_wait INT,
	longest_wait INT,
	sla_met FLOAT
);

CREATE TABLE call_logs (
	call_id UUID PRIMARY KEY,
	agent_id INT,
	queue queue_enum,
	start_time TIMESTAMP,
	end_time TIMESTAMP,
	duration INT,
	type call_type_enum,
	status status_enum,
	FOREIGN KEY (agent_id) REFERENCES agents(agent_id)
);